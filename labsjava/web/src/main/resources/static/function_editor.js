let modalCreateFuncStep1 = new bootstrap.Modal(document.getElementById("modalCreateFuncStep1"))
let modalCreateFuncStep2 = new bootstrap.Modal(document.getElementById("modalCreateFuncStep2"))
let modalImportFunc = new bootstrap.Modal(document.getElementById("modalImportFunc"))

let modalCreateFuncStep1__count = document.getElementById("modalCreateFuncStep1__count")
let modalCreateFuncStep1__mathFunc = document.getElementById("modalCreateFuncStep1__mathFunc")
let modalCreateFuncStep1__additional = document.getElementById("modalCreateFuncStep1__additional")
let modalCreateFuncStep1__xFrom = document.getElementById("modalCreateFuncStep1__xFrom")
let modalCreateFuncStep1__xTo = document.getElementById("modalCreateFuncStep1__xTo")
let modalCreateFuncStep1__fabric = document.getElementById("modalCreateFuncStep1__fabric")

let modalCreateFuncStep2__tbody = document.querySelector("#modalCreateFuncStep2__table > tbody")
let modalCreateFuncStep2__template_row = document.getElementById("modalCreateFuncStep2__template_row")

let modalImportFunc__inputFile = document.getElementById("modalImportFunc__inputFile")

let operand_export_button = document.getElementById("operand_export_button")
let operand_table = document.getElementById("operand_table")


let view_table__template_row = document.getElementById("view_table__template_row")

let operand_id = 0
let resultFunc_id = 0
let functionChart = null;
let applyX = document.getElementById("applyX");
let applyResult = document.getElementById("applyResult");
let applyResultValue = document.getElementById("applyResultValue");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MODAL CREATE TABULATED FUNCTION STEP 1

function openStep1ModalCreateFunc() {
    fetch("/api/functions/l10n", {
        method: "GET",
        headers: { "Accept": "application/json" }
    })
        .then(resp => resp.json())
        .then(json => {
            console.log(json)

            modalCreateFuncStep1__mathFunc.replaceChildren()

            let optionNone = document.createElement("option")
            optionNone.selected = true
            optionNone.value = "NONE"
            optionNone.text = "(не использовать)"
            modalCreateFuncStep1__mathFunc.appendChild(optionNone)

            let sortable = __sortL10N(json)

            for (let it of sortable) {
                let option = document.createElement("option")
                option.value = it.k
                option.text = it.v
                modalCreateFuncStep1__mathFunc.appendChild(option)
            }

            modalCreateFuncStep1.show()
        })
        .catch(err => console.log(err))
}

function onMCFS1ChangeMathFunc() {
    if (modalCreateFuncStep1__mathFunc.value === "NONE") {
        modalCreateFuncStep1__additional.style.display = "none"
    } else {
        modalCreateFuncStep1__additional.style.display = ""
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MODAL CREATE TABULATED FUNCTION STEP 2

function nextStep2ModalCreateFunc() {
    let count = modalCreateFuncStep1__count.value
    modalCreateFuncStep2__tbody.replaceChildren()

    for (let i = 1; i <= count; i++) {
        let cloned = modalCreateFuncStep2__template_row.content.cloneNode(true);
        let td = cloned.querySelector("tr > td:nth-child(1)");
        td.textContent = i
        modalCreateFuncStep2__tbody.appendChild(cloned);
    }

    modalCreateFuncStep1.hide()
    modalCreateFuncStep2.show()
}

function submitCreateFunc() {
    let request = {}

    request["count"] = modalCreateFuncStep1__count.value
    request["fabric_type"] = modalCreateFuncStep1__fabric.value

    if (modalCreateFuncStep1__mathFunc.value !== "NONE") {
        request["math_func"] = modalCreateFuncStep1__mathFunc.value
        request["x_from"] = modalCreateFuncStep1__xFrom.value
        request["x_to"] = modalCreateFuncStep1__xTo.value
    }

    request["points"] = []
    let inputs = modalCreateFuncStep2__tbody.querySelectorAll("input")
    for (let i = 0; i < inputs.length; i = i + 2) {
        request["points"].push({
            x: inputs[i].value,
            y: inputs[i + 1].value
        })
    }

    fetch("/api/functions", {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request)
    })
        .then(resp => resp.json())
        .then(json => {
            console.log(json)

            operand_id = json.math_func_id
            operand_export_button.disabled = false


            let tbody = operand_table.querySelector("tbody")
            tbody.replaceChildren()

            let count = json.points.length
            for (let i = 0; i < count; i++) {
                let cloned = view_table__template_row.content.cloneNode(true);

                let td = cloned.querySelector("tr > td:nth-child(1)");
                let inputX = cloned.querySelector("input[data-wu-point='x']")
                let inputY = cloned.querySelector("input[data-wu-point='y']")

                td.textContent = i + 1
                inputX.value = json.points[i].x
                inputY.value = json.points[i].y

                inputX.disabled = true
                inputY.onchange = updateFunc.bind(inputY, i)

                tbody.appendChild(cloned);
            }
            const points = getPointsFromTable(operand_table);
            updateChart(points);

            modalCreateFuncStep2.hide()
        })
        .catch(err => console.log(err))
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// IMPORT FUNCTIONS

function openImportFuncModal() {
    modalImportFunc.show()
}

function submitImport() {
    let data = new FormData()
    data.append('file', modalImportFunc__inputFile.files[0])

    fetch("/api/functions/import", {
        method: "POST",
        body: data
    })
        .then(resp => resp.json())
        .then(json => {
            console.log(json)

            operand_id = json.math_func_id
            operand_export_button.disabled = false


            let tbody = operand_table.querySelector("tbody")
            tbody.replaceChildren()

            let count = json.points.length
            for (let i = 0; i < count; i++) {
                let cloned = view_table__template_row.content.cloneNode(true);

                let td = cloned.querySelector("tr > td:nth-child(1)");
                let inputX = cloned.querySelector("input[data-wu-point='x']")
                let inputY = cloned.querySelector("input[data-wu-point='y']")

                td.textContent = i + 1
                inputX.value = json.points[i].x
                inputY.value = json.points[i].y

                inputX.disabled = true
                inputY.onchange = updateFunc.bind(inputY, i)

                tbody.appendChild(cloned);
            }

            const points = getPointsFromTable(operand_table);
            updateChart(points);

            modalImportFunc.hide()
        })
        .catch(err => console.error(err))
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// GENERAL FUNCTIONS
function cancelModalCreateFunc() {
    modalCreateFuncStep1.hide()
    modalCreateFuncStep2.hide()
}

function updateFunc(pointIdx) {
    //this = inputY

    if (!this.checkValidity()) {
        return
    }

    let request = {}
    request["point_index"] = pointIdx
    request["value"] = this.value

    fetch(`/api/functions/${operand_id}`, {
        method: "PATCH",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request)
    })
        .catch(err => console.log(err));
    const points = getPointsFromTable(operand_table);
    updateChart(points);
}

function exportFunc(operand_num) {
    let mathFuncId
    if (operand_num === 1) {
        mathFuncId = operand_id
    } else if (operand_num === 2) {
        mathFuncId = resultFunc_id
    } else {
        return
    }

    window.open(`/api/functions/${mathFuncId}/export`, '_blank').focus();
}

function getPointsFromTable(table) {
    console.log("Table:", table); // Debug
    const points = [];
    const rows = table.querySelectorAll('tbody tr');
    console.log("Rows found:", rows.length); // Debug

    Array.from(rows).slice(1).forEach((row, index) => {
        const xInput = row.querySelector('input[data-wu-point="x"]');
        const yInput = row.querySelector('input[data-wu-point="y"]');
        console.log(`Row ${index + 1}:`, {xInput, yInput}); // Debug

        const x = parseFloat(xInput.value);
        const y = parseFloat(yInput.value);
        console.log(`Points ${index + 1}:`, {x, y}); // Debug

        if (!isNaN(x) && !isNaN(y)) {
            points.push({x, y});
        }
    });

    console.log("Final points:", points); // Debug
    return points;
}

function updateChart(points) {
    if (functionChart) {
        functionChart.destroy();
    }

    const ctx = document.getElementById('functionChart').getContext('2d');
    console.log(points)
    functionChart = new Chart(ctx, {
        type: 'line',
        data: {
            datasets: [{
                label: 'Function',
                data: points,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: {
                    type: 'linear',
                    position: 'bottom'
                }
            }
        }
    });
}

function submitApply() {
    const xValue = parseFloat(applyX.value);

    if (isNaN(xValue)) {
        alert('Please enter a valid number');
        return;
    }

    if (!operand_id) {
        alert('Please create or import a function first');
        return;
    }

    const request = {
        xValue: xValue
    };

    fetch(`/api/functions/${operand_id}/apply`, {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request)
    })
        .then(resp => resp.json())
        .then(json => {
            applyResultValue.textContent = json.result;
            applyResult.style.display = 'block';
        })
        .catch(err => {
            console.error(err);
            alert('Error applying function');
        });
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// PRIVATE FUNCTIONS

function __sortL10N(json) {
    let sortable = []

    for (let key in json) {
        if (!json.hasOwnProperty(key)) continue;
        sortable.push({k: key, v: json[key]})
    }
    sortable.sort((a, b) => (a.v > b.v) ? 1 : -1)

    return sortable
}
