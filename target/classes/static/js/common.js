function selectOpt(ele) {

    var $selectEle = $(".form-select." + ele);

    $selectEle.select2({
        placeholder: "선택",
        allowClear: false
    });

    $selectEle.on("select2:select", function (e) {
        var selVal = $selectEle.val();
        var selTxt = $selectEle.select2('data')[0].text;

        if (ele == "emailSelection") {
            if (selVal != "typing") {
                $("#email_domain").val(selTxt);
                $("#email_domain").attr("readonly", true);
            }
            else {
                $("#email_domain").val("").attr("readonly", false).focus();
            }

        }
        else if (ele == "emailSelection") {
            if (selVal != "typing") {
                $("#email_domain").val(selTxt);
                $("#email_domain").attr("readonly", true);
            }
            else {
                $("#email_domain").val("").attr("readonly", false).focus();
            }

        }
        else if (ele == "mil_srvc") {

            if (selVal == "면제") {
                $("#mil_exem").prop("disabled", false);
                $("#mil_type, #mil_type_rank, #mil_discharge").prop("disabled", true).val("").trigger("change");
                $("#mil_start_date, #mil_end_date").prop("disabled", true).val("");
            }
            else if (selVal == "비대상" || selVal == "미필") {
                $("#mil_exem").prop("disabled", true).val("");
                $("#mil_type, #mil_type_rank, #mil_discharge").prop("disabled", true).val("").trigger("change");
                $("#mil_start_date, #mil_end_date").prop("disabled", true).val("");
            }
            else {
                $("#mil_exem").prop("disabled", true).val("");
                $("#mil_type, #mil_type_rank, #mil_discharge, #mil_start_date, #mil_end_date").prop("disabled", false)
            }
        }
        else if (ele == "vtr_srvc") {
            if (selVal == "F") {
                $("#vtr_relation, #vtr_num").prop("disabled", true).val("").trigger("change");
            }
            else if (selVal == "T") {
                $("#vtr_relation, #vtr_num").prop("disabled", false);
            }
        }
        else if (ele == "dis_srvc") {
            if (selVal == "F") {
                $("#dis_class, #dis_rate").prop("disabled", true).val("").trigger("change");
            }
            else if (selVal == "T") {
                $("#dis_class, #dis_rate").prop("disabled", false);
            }
        }
        else if (ele == "co_sts") {
            if (selVal == "재직") {
                $('[name=co_end_date]').prop("disabled", true).val("").trigger("change");
            }
            else {
                $('[name=co_end_date]').prop("disabled", false);
            }
        }
    });
}

$('.date').datepicker({
    format: "yyyy-mm-dd",
    language: "kr",
    autoclose: true
});

$('.dateMonth input').each(function () {
    $(this).datepicker({
        format: 'yyyy-mm',
        language: "kr",
        minViewMode: "months",
        autoclose: true
    })
});

function validateField(value, message, silentMode) {
    if (!$('#' + value).val() && !silentMode) {
        alert(message);
        $("#" + value).focus();
        return false;
    }
    return true;
}

function validateFieldName(name, message, silentMode) {
    if (!$("input[name='" + name + "']:checked").val() && !silentMode) {
        alert(message);
        $("input[name='" + name + "']:first").focus();
        return false;
    }
    return true;
}

function validateFieldAdr(value, message, silentMode) {
    if (!$('#' + value).val() && !silentMode) {
        alert(message);
        $("#postcode_btn").focus();
        return false;
    }
    return true;
}

function validateDate(startDate, endDate, silentMode) {
    var startValue = $('#' + startDate).val();
    var endValue = $('#' + endDate).val();

    if ((!startValue || !endValue) && !silentMode) {
        alert("날짜를 입력해 주세요.");
        if (!startValue) {
            $("#" + startDate).focus();
        } else {
            $("#" + endDate).focus();
        }
        return false;
    } else if ((startValue > endValue) && !silentMode) {
        alert("시작일이 종료일보다 늦을 수 없습니다.");
        $("#" + startDate).focus();
        return false;
    }
    return true;
}

function validateCrd(crd, maxCrd, silentMode) {
    var isValid = true;

    $("input[name='" + crd + "']").each(function (index) {
        // crd와 maxCrd 입력 필드 값 가져오기
        var crdValue = $(this).val();
        var maxCrdValue = $("input[name='" + maxCrd + "']").eq(index).val();

        if (!crdValue || !maxCrdValue) {
            if (!silentMode) {
                if (!crdValue) alert("취득학점을 입력해 주세요.");
                else alert("최대학점을 입력해 주세요.");
            }
            $(this).focus();
            isValid = false;
            return false;
        } else if (parseFloat(crdValue) > parseFloat(maxCrdValue)) {
            if (!silentMode) alert("취득학점이 최대학점보다 많을 수 없습니다.");
            $(this).focus();
            isValid = false;
            return false;
        }
    });

    return isValid;
}


/* 다수값 유효성 검사 */
function validateSelect(name, message, silentMode) {
    var isValid = true;

    $("select[name='" + name + "']").each(function () {
        var value = $(this).val();

        if (!value) {
            if (!silentMode) alert(message);
            isValid = false;
            return silentMode;
        }
    });

    return isValid;
}

function validateInput(name, message) {
    var isValid = true;

    $("input[name='" + name + "']").each(function () {
        var value = $(this).val();

        if (!value) {
            if (!silentMode) alert(message);
            isValid = silentMode;
            return false;
        }
    });

    return isValid;
}