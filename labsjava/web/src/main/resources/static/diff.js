let modalCreateFuncStep1 = new bootstrap.Modal(document.getElementById("modalCreateFuncStep1"))
let modalCreateFuncStep2 = new bootstrap.Modal(document.getElementById("modalCreateFuncStep2"))
let modalImportFunc = new bootstrap.Modal(document.getElementById("modalImportFunc"))

let modalError = new bootstrap.Modal(document.getElementById("modalError"))
let modalError__message = document.getElementById("modalError__message")

let modalCreateFuncStep1__count = document.getElementById("modalCreateFuncStep1__count")
let modalCreateFuncStep1__mathFunc = document.getElementById("modalCreateFuncStep1__mathFunc")
let modalCreateFuncStep1__additional = document.getElementById("modalCreateFuncStep1__additional")
let modalCreateFuncStep1__xFrom = document.getElementById("modalCreateFuncStep1__xFrom")
let modalCreateFuncStep1__xTo = document.getElementById("modalCreateFuncStep1__xTo")
let modalCreateFuncStep1__fabric = document.getElementById("modalCreateFuncStep1__fabric")
let modalCreateFuncStep1__nextBtn = document.getElementById("modalCreateFuncStep1__nextBtn")
let modalCreateFuncStep1__createBtn = document.getElementById("modalCreateFuncStep1__createBtn")

let modalCreateFuncStep2__tbody = document.querySelector("#modalCreateFuncStep2__table > tbody")
let modalCreateFuncStep2__template_row = document.getElementById("modalCreateFuncStep2__template_row")

let modalImportFunc__inputFile = document.getElementById("modalImportFunc__inputFile")

let operand_export_button = document.getElementById("operand_export_button")
let operand_table = document.getElementById("operand_table")

let result_table__submit = document.getElementById("result_table__submit")
let result_table__fabric = document.getElementById("result_table__fabric")
let result_export_button = document.getElementById("result_export_button")
let result_table = document.getElementById("result_table")

let view_table__template_row = document.getElementById("view_table__template_row")

let operand_id = 0
let resultFunc_id = 0

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
            if ("error_class" in json) {
                __openModalError(`${json.error_class}: ${json.error_message}`)
                return
            }


            modalCreateFuncStep1__mathFunc.replaceChildren()

            let optionNone = document.createElement("option")
            optionNone.selected = true
            optionNone.value = "NONE"
            optionNone.text = "(не использовать)"
            modalCreateFuncStep1__mathFunc.appendChild(optionNone)
            modalCreateFuncStep1__additional.style.display = "none"

            let sortable = __sortL10N(json)

            for (let it of sortable) {
                let option = document.createElement("option")
                option.value = it.k
                option.text = it.v
                modalCreateFuncStep1__mathFunc.appendChild(option)
            }

            modalCreateFuncStep1.show()
        })
        .catch(err => {
            console.log(err)
            __openModalError(JSON.stringify(err))
        })
}

function onMCFS1ChangeMathFunc() {
    if (modalCreateFuncStep1__mathFunc.value === "NONE") {
        modalCreateFuncStep1__additional.style.display = "none"
        modalCreateFuncStep1__nextBtn.style.display = ""
        modalCreateFuncStep1__createBtn.style.display = "none"
    } else {
        modalCreateFuncStep1__additional.style.display = ""
        modalCreateFuncStep1__nextBtn.style.display = "none"
        modalCreateFuncStep1__createBtn.style.display = ""
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
    } else {
        request["points"] = []
        let inputs = modalCreateFuncStep2__tbody.querySelectorAll("input")
        for (let i = 0; i < inputs.length; i = i + 2) {
            request["points"].push({
                x: inputs[i].value,
                y: inputs[i + 1].value
            })
        }
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
            if ("error_class" in json) {
                modalCreateFuncStep1.hide()
                modalCreateFuncStep2.hide()
                __openModalError(`${json.error_class}: ${json.error_message}`)
                return
            }

            operand_id = json.math_func_id
            operand_export_button.disabled = false

            result_table__submit.disabled = false

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

            modalCreateFuncStep1.hide()
            modalCreateFuncStep2.hide()
        })
        .catch(err => {
            console.log(err)
            __openModalError(JSON.stringify(err))
        })
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
            if ("error_class" in json) {
                modalImportFunc.hide()
                __openModalError(`${json.error_class}: ${json.error_message}`)
                return
            }

            operand_id = json.math_func_id
            operand_export_button.disabled = false

            result_table__submit.disabled = false

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

            modalImportFunc.hide()
        })
        .catch(err => {
            console.log(err)
            __openModalError(JSON.stringify(err))
        })
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// GENERAL FUNCTIONS

function cancelModalCreateFunc() {
    modalCreateFuncStep1.hide()
    modalCreateFuncStep2.hide()
}

function submitOperation() {
    let request = {}

    request["fabric_type"] = result_table__fabric.value

    fetch(`/api/functions/${operand_id}/diff`, {
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
            if ("error_class" in json) {
                __openModalError(`${json.error_class}: ${json.error_message}`)
                return
            }

            resultFunc_id = json.math_func_id
            result_export_button.disabled = false

            let tbody = result_table.querySelector("tbody")
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
                inputY.disabled = true

                tbody.appendChild(cloned);
            }
        })
        .catch(err => {
            console.log(err)
            modalError__message.innerText = JSON.stringify(err)
            modalError.show()
        })
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
        .then(resp => resp.json())
        .then(json => {
            if ("error_class" in json) {
                console.log(json)
                __openModalError(`${json.error_class}: ${json.error_message}`)
            }
        })
        .catch(err => {
            console.log(err)
            __openModalError(JSON.stringify(err))
        })
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

function __openModalError(message) {
    modalError__message.innerText = message
    modalError.show()
}
