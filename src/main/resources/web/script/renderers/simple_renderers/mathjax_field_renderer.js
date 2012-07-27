//  MathJax.js (TeX) renderer for "tub.eduzen.excercise_description",
//      "tub.eduzen.excercise_object" and "tub.eduzen.approach.content"
dm4c.add_simple_renderer('tub.eduzen.mathjax_field_renderer', {

    render_info: function(model, $parent) {
        // 
        if (typeof(model) == "object") {
            var value = model.value
        } else {
            var value = model
        }
        // render label
        dm4c.render.field_label(model, $parent)
        var html = '<div class="task content" id="content" style="border: 1px solid #e8e8e8;">'
            + '<div id="math-output" class="output">' + value + '</div>'
            + '</div>'
        $parent.append(html)
        // interpret just math in div#math-output
        // MathJax.Hub.Queue(["Typeset", MathJax.Hub, "math-output"])
        // and/or typeset whole page-panel once
        MathJax.Hub.Queue(["Typeset", MathJax.Hub, "page-panel"])
        // --------------- "Update", "Typeset", "Process"
    },

    // ### support multiple form_renderer elements in one page-panel through unique dom-ids and/or class selector
    render_form: function(model, $parent) {
        //
        if (typeof(model) == "object") {
            var value = model.value
        } else {
            var value = model
        }
        var $content = $('<textarea id="math-input" type="text" rows="4">').attr({ value : value, size : 80 })
        //
        $parent.append($content)
        dm4c.render.field_label("Vorschau", $parent)
        var html = '<div class="task content preview" id="content" style="border: 1px solid #e8e8e8;">'
            + '<div id="math-output" class="output">' + value + '</div>'
            + '</div>'
        $parent.append(html)
        // register keyup-handler on math-input field
        $('#math-input').keyup(function () {
            $("#math-output").text($('#math-input').val())
            MathJax.Hub.Queue(["Typeset", MathJax.Hub, "math-output"])
        })
        return function() { // return input value
            return $.trim($content.val())
        }
    }

})
