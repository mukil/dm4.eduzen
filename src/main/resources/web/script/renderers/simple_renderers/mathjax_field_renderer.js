//  MathJax.js (TeX) simple renderer and single-editor for "tub.eduzen.excercise_description",
//      "tub.eduzen.excercise_object" and "tub.eduzen.approach.content"
dm4c.add_simple_renderer('tub.eduzen.mathjax_field_renderer', {

    render_info: function(model, $parent) {

        // render label
        dm4c.render.field_label(model, $parent)
        var html = '<div class="task content" id="content">'
            + '<div id="math-output" class="output">' + model.value + '</div>'
            + '</div>'
        $parent.append(html)

        // typeset all elements containing TeX to SVG in #page-content
        MathJax.Hub.Typeset()

        },

    render_form: function(model, $parent) {
        var elementInputId = 'math-input-' + model.topic.id
        var elementOutputId = 'math-output-' + model.topic.id
        var $content = $('<textarea id="' + elementInputId +
            '" type="text" rows="4">').attr({ value : model.value, size : 80 })

        $parent.append($content)
        dm4c.render.field_label("Preview", $parent)
        var html = '<div id="math-preview" class="math">'
            + '<div id="' + elementOutputId + '" class="output">' + model.value + '</div>'
            + '</div>'
        $parent.append(html)

        // register keyup-handler on math-input textarea
        $('#' + elementInputId).keyup(function () {
            $('#' + elementOutputId).text($('#' + elementInputId).val())
            MathJax.Hub.Queue(["Typeset", MathJax.Hub, elementOutputId])
        })

        // ### fixme: implement proper return function, with usage of dm4c.REF_PREFIX to reference existing ones
        return function() { // return input value
            return $.trim($content.val())
        }
    }

})
