package web.controller;

import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;
import io.FunctionsIO;
import operations.TabulatedDifferentialOperator;
import operations.TabulatedFunctionOperationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.dto.*;
import web.utils.MathFunctionEnum;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/functions")
public class MathFunctionController {
    private final Map<String, String> nameFabricMap;
    private final Map<String, TabulatedFunctionFactory> tabulatedFunctionFactoryMap;

    private final AtomicInteger atomicInteger;
    private final Map<Integer, TabulatedFunction> tabulatedFunctionMap;

    public MathFunctionController(@Qualifier("nameFabricMap") Map<String, String> nameFabricMap,
                                  Map<String, TabulatedFunctionFactory> tabulatedFunctionFactoryMap) {
        this.nameFabricMap = nameFabricMap;
        this.tabulatedFunctionFactoryMap = tabulatedFunctionFactoryMap;

        this.atomicInteger = new AtomicInteger(1);
        this.tabulatedFunctionMap = new HashMap<>();
    }

    @PostMapping
    public MathFunctionResponse create(@RequestBody MathFunctionCreateRequest request) {
        TabulatedFunctionFactory fabric;
        if (StringUtils.hasText(request.getFabricType())) {
            fabric = tabulatedFunctionFactoryMap.get(nameFabricMap.get(request.getFabricType()));
        } else {
            fabric = tabulatedFunctionFactoryMap.get("arrayTabulatedFunctionFactory");
        }

        TabulatedFunction tabulatedFunction;
        if (StringUtils.hasText(request.getMathFunc())) {
            var mathFunctionEnum = MathFunctionEnum.valueOf(request.getMathFunc());
            tabulatedFunction = fabric.create(
                    mathFunctionEnum.getFabric().get(),
                    request.getXFrom(),
                    request.getXTo(),
                    request.getCount()
            );
        } else {
            double[] xValues = new double[request.getCount()];
            double[] yValues = new double[request.getCount()];

            int idx = 0;
            for (PointDto point : request.getPoints()) {
                xValues[idx] = point.getX();
                yValues[idx] = point.getY();
                idx++;
            }

            tabulatedFunction = fabric.create(xValues, yValues);
        }

        int id = atomicInteger.getAndIncrement();
        tabulatedFunctionMap.put(id, tabulatedFunction);

        var response = new MathFunctionResponse();
        response.setId(id);
        response.setPoints(new LinkedList<>());
        tabulatedFunction.iterator().forEachRemaining(p -> {
            response.getPoints().add(new PointDto(p.x, p.y));
        });

        return response;
    }

    @GetMapping("/{id}")
    public MathFunctionResponse get(@PathVariable(name = "id") Integer mathFuncId) {
        var tabulatedFunction = tabulatedFunctionMap.get(mathFuncId);

        var response = new MathFunctionResponse();
        response.setId(mathFuncId);
        response.setPoints(new LinkedList<>());
        tabulatedFunction.iterator().forEachRemaining(p -> {
            response.getPoints().add(new PointDto(p.x, p.y));
        });

        return response;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateYPoint(
            @PathVariable(name = "id") Integer mathFuncId,
            @RequestBody MathFunctionUpdateYRequest request
    ) {
        var tabulatedFunction = tabulatedFunctionMap.get(mathFuncId);
        tabulatedFunction.setY(request.getPointIndex(), request.getValue());
    }

    @PostMapping("/operation")
    public MathFunctionResponse operation(@RequestBody MathFunctionOperationRequest request) {
        var tabulatedFunction1 = tabulatedFunctionMap.get(request.getOperandId1());
        var tabulatedFunction2 = tabulatedFunctionMap.get(request.getOperandId2());

        TabulatedFunctionFactory fabric;
        if (StringUtils.hasText(request.getFabricType())) {
            fabric = tabulatedFunctionFactoryMap.get(nameFabricMap.get(request.getFabricType()));
        } else {
            fabric = tabulatedFunctionFactoryMap.get("arrayTabulatedFunctionFactory");
        }

        var operationService = new TabulatedFunctionOperationService(fabric);

        TabulatedFunction tabulatedFunctionResult;
        switch (request.getOperation()) {
            case "ADD": {
                tabulatedFunctionResult = operationService.add(tabulatedFunction1, tabulatedFunction2);
                break;
            }
            case "SUBTRACT": {
                tabulatedFunctionResult = operationService.subtract(tabulatedFunction1, tabulatedFunction2);
                break;
            }
            case "MULTIPLY": {
                tabulatedFunctionResult = operationService.multiply(tabulatedFunction1, tabulatedFunction2);
                break;
            }
            case "DIVIDE": {
                tabulatedFunctionResult = operationService.divide(tabulatedFunction1, tabulatedFunction2);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown operation: " + request.getOperation());
            }
        }

        int id = atomicInteger.getAndIncrement();
        tabulatedFunctionMap.put(id, tabulatedFunctionResult);

        var response = new MathFunctionResponse();
        response.setId(id);
        response.setPoints(new LinkedList<>());
        tabulatedFunctionResult.iterator().forEachRemaining(p -> {
            response.getPoints().add(new PointDto(p.x, p.y));
        });

        return response;
    }

    @PostMapping("/{id}/diff")
    public MathFunctionResponse diff(
            @PathVariable(name = "id") Integer mathFuncId,
            @RequestBody MathFunctionDiffRequest request
    ) {
        var tabulatedFunction = tabulatedFunctionMap.get(mathFuncId);

        TabulatedFunctionFactory fabric;
        if (StringUtils.hasText(request.getFabricType())) {
            fabric = tabulatedFunctionFactoryMap.get(nameFabricMap.get(request.getFabricType()));
        } else {
            fabric = tabulatedFunctionFactoryMap.get("arrayTabulatedFunctionFactory");
        }

        var differentialOperator = new TabulatedDifferentialOperator(fabric);
        var tabulatedFunctionResult = differentialOperator.derive(tabulatedFunction);

        int id = atomicInteger.getAndIncrement();
        tabulatedFunctionMap.put(id, tabulatedFunctionResult);

        var response = new MathFunctionResponse();
        response.setId(id);
        response.setPoints(new LinkedList<>());
        tabulatedFunctionResult.iterator().forEachRemaining(p -> {
            response.getPoints().add(new PointDto(p.x, p.y));
        });

        return response;
    }

    @GetMapping(path = "/{id}/export",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportFunc(@PathVariable(name = "id") Integer mathFuncId) throws IOException {
        var tabulatedFunction = tabulatedFunctionMap.get(mathFuncId);

        ResponseEntity<byte[]> response;
        try (var baos = new ByteArrayOutputStream();
             var bos = new BufferedOutputStream(baos)) {
            FunctionsIO.serialize(bos, tabulatedFunction);
            var filename = "tabulatedFunction-%d.bin".formatted(mathFuncId);

            response = ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Disposition", "inline; filename=\"%s\"".formatted(filename))
                    .body(baos.toByteArray());
        }

        return response;
    }

    @PostMapping(path = "/import")
    public MathFunctionResponse importFunc(@RequestParam("file") MultipartFile file) throws IOException, ClassNotFoundException {
        TabulatedFunction tabulatedFunction;

        try (var inputStream = file.getInputStream();
             var bis = new BufferedInputStream(inputStream)) {
            tabulatedFunction = FunctionsIO.deserialize(bis);
        }

        var id = atomicInteger.getAndIncrement();
        tabulatedFunctionMap.put(id, tabulatedFunction);

        var response = new MathFunctionResponse();
        response.setId(id);
        response.setPoints(new LinkedList<>());
        tabulatedFunction.iterator().forEachRemaining(p -> {
            response.getPoints().add(new PointDto(p.x, p.y));
        });

        return response;
    }

    @PostMapping("/{id}/apply")
    public MathFunctionApplyResponse applyFunc(
            @PathVariable(name = "id") Integer mathFuncId,
            @RequestBody MathFunctionApplyRequest request
    ) {
        var tabulatedFunction = tabulatedFunctionMap.get(mathFuncId);

        var tabulatedFunctionResult = tabulatedFunction.apply(request.getXValue());


        var response = new MathFunctionApplyResponse();
        response.setResult(tabulatedFunctionResult);

        return response;
    }

    // L10N = Localization
    @GetMapping("/l10n")
    public Map<String, String> getL10n() {
        return Arrays.stream(MathFunctionEnum.values()).collect(Collectors.toMap(
                Enum::name,
                MathFunctionEnum::getLocalization
        ));
    }
}
