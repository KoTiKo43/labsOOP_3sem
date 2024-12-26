package web.utils;

import functions.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Getter
public enum MathFunctionEnum {
    IDENTITY(new Supplier<MathFunction>() {
        @Override
        public MathFunction get() {
            return new IdentityFunction();
        }
    }, "Тождественная функция"),
    SQR(() -> new SqrFunction(), "Квадратичная функция"),
    UNIT(UnitFunction::new, "Единичная функция"),
    ZERO(ZeroFunction::new, "Нулевая функция");

    private final Supplier<MathFunction> fabric;
    private final String localization;
}
