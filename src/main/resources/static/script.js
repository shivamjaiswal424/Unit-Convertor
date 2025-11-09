$(document).ready(function () {
    // Buttons
    const lengthButton = $("#length-button");
    const weightButton = $("#weight-button");
    const temperatureButton = $("#temperature-button");

    // Forms
    const lengthForm = $("#length-form");
    const weightForm = $("#weight-form");
    const temperatureForm = $("#temperature-form");

    const resultForm = $("#resultForm");

    // --- UI toggles ---
    lengthButton.click(function () {
        lengthForm.show();
        weightForm.hide();
        temperatureForm.hide();

        lengthButton.addClass("active");
        weightButton.removeClass("active");
        temperatureButton.removeClass("active");
        clearResult();
    });

    weightButton.click(function () {
        lengthForm.hide();
        weightForm.show();
        temperatureForm.hide();

        lengthButton.removeClass("active");
        weightButton.addClass("active");
        temperatureButton.removeClass("active");
        clearResult();
    });

    temperatureButton.click(function () {
        lengthForm.hide();
        weightForm.hide();
        temperatureForm.show();

        lengthButton.removeClass("active");
        weightButton.removeClass("active");
        temperatureButton.addClass("active");
        clearResult();
    });

    // --- Helpers ---
    function clearResult() {
        resultForm.hide().html("");
    }

    function showError(msg) {
        resultForm.show().html(`<span class="error">Error: ${msg}</span>`);
    }

    function fmtNumber(n) {
        // Allow large/small numbers to be readable
        if (typeof n !== "number") return n;
        // If too many decimals, limit to 6 and trim trailing zeros
        const fixed = Math.abs(n) >= 1e6 || Math.abs(n) < 1e-4
            ? n.toExponential(6)
            : n.toLocaleString(undefined, { maximumFractionDigits: 6 });
        return fixed;
    }

    function unitLabel(u, isTemp = false) {
        if (!isTemp) return u;
        // Pretty symbols for temperature
        switch (u) {
            case "C": return "°C";
            case "F": return "°F";
            case "K": return "K";
            default:  return u;
        }
    }

    function showResultPretty(response, isTemp = false) {
        resultForm.show();

        if (response && typeof response.result !== "undefined") {
            const value = response.value;
            const from = response.from;
            const to = response.to;
            const result = response.result;

            const left = `${fmtNumber(Number(value))} ${unitLabel(from, isTemp)}`;
            const right = `${fmtNumber(Number(result))} ${unitLabel(to, isTemp)}`;

            resultForm.html(`
                <div class="result-text">
                    <strong>${left}</strong> = <span class="highlight">${right}</span>
                </div>
            `);
        } else if (response && response.message) {
            showError(response.message);
        } else {
            showError("Unexpected response");
        }
    }

    function validateInputs(val, from, to) {
        if (val === "" || val === null || typeof val === "undefined") {
            return "Please enter a value.";
        }
        if (isNaN(Number(val))) {
            return "Value must be a number.";
        }
        if (!from) return "Please select a 'From' unit.";
        if (!to) return "Please select a 'To' unit.";
        return null;
    }

    // --- Submit handlers ---
    lengthForm.submit(function (event) {
        event.preventDefault();

        const data = {
            from: $("#length-from").val(),
            to: $("#length-to").val(),
            value: $("#length-value").val()
        };

        const err = validateInputs(data.value, data.from, data.to);
        if (err) return showError(err);

        $.ajax({
            url: "/convert/length",
            type: "POST",
            data: JSON.stringify({ ...data, value: Number(data.value) }),
            contentType: "application/json"
        })
            .done(function (response) {
                showResultPretty(response, false);
            })
            .fail(function (xhr) {
                const msg = (xhr.responseJSON && xhr.responseJSON.message) || "Conversion failed.";
                showError(msg);
            });
    });

    weightForm.submit(function (event) {
        event.preventDefault();

        const data = {
            from: $("#weight-from").val(),
            to: $("#weight-to").val(),
            value: $("#weight-value").val()
        };

        const err = validateInputs(data.value, data.from, data.to);
        if (err) return showError(err);

        $.ajax({
            url: "/convert/weight",
            type: "POST",
            data: JSON.stringify({ ...data, value: Number(data.value) }),
            contentType: "application/json"
        })
            .done(function (response) {
                showResultPretty(response, false);
            })
            .fail(function (xhr) {
                const msg = (xhr.responseJSON && xhr.responseJSON.message) || "Conversion failed.";
                showError(msg);
            });
    });

    temperatureForm.submit(function (event) {
        event.preventDefault();

        const data = {
            from: $("#temperature-from").val(),
            to: $("#temperature-to").val(),
            value: $("#temperature-value").val()
        };

        const err = validateInputs(data.value, data.from, data.to);
        if (err) return showError(err);

        $.ajax({
            url: "/convert/temperature",
            type: "POST",
            data: JSON.stringify({ ...data, value: Number(data.value) }),
            contentType: "application/json"
        })
            .done(function (response) {
                showResultPretty(response, true); // pretty temperature units
            })
            .fail(function (xhr) {
                const msg = (xhr.responseJSON && xhr.responseJSON.message) || "Conversion failed.";
                showError(msg);
            });
    });
});
