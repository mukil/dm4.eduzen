// MathJax.js (TeX) renderer for "dm4.eduzen.task.content" and "dm4.eduzen.suggestion.content"
dm4c.add_simple_renderer('dm4.eduzen.task.content_field_renderer', {

    render_info: function(model, $parent) {
        // render label
        dm4c.render.field_label(model, $parent)
        var html = '<div class="task content" id="content" style="border: 1px solid #e8e8e8;">'
            + '<div id="math-output" class="output">' + model.value + '</div>'
            + '</div>'
        $parent.append(html)
        // interpret just math in div#math-output
        // MathJax.Hub.Queue(["Typeset", MathJax.Hub, "math-output"])
        // and/or typeset whole page-panel once
        MathJax.Hub.Queue(["Typeset", MathJax.Hub, "page-panel"])
        // --------------- "Update", "Typeset", "Process"
    },

    render_form: function(model, $parent) {
        //
        if (typeof(model) == "object") {
            var value = model.value
        } else {
            var value = model
        }
        // #### $('#math-input').unbind() // free DOMElement of all event listeners dm4-webclient may did register
        var $content = $('<textarea id="math-input" type="text" rows="4">').attr({ value : value, size : 80 })
        //
        $parent.append($content)
        //
        return function() { // return input value
            return $.trim($content.val())
        }
    }

})
