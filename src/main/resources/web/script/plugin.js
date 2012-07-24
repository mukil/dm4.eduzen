dm4c.add_plugin("de.deepamehta.eduzen", function() {

    // -------------------------------------------------------- 
    dm4c.load_script('/de.deepamehta.eduzen/script/vendor/mathjax/MathJax.js?delayStartupUntil=configured')
    // 
    // dm4c.load_script('/de.deepamehta.eduzen/script/vendor/mathjax/extensions/v1.0-warning.js')

    // define type specific commands and register them
    dm4c.add_listener('init', function (e) {
        // say hello to
        console.log("EduZEN-Plugin.initializing(0.1.0-SNAPSHOT) ... ")
        // ### the workaround to load all important mathjax stuff, except the one file commented above^^
        MathJax.Ajax.config.root = "http://"+ window.location.host + "/de.deepamehta.eduzen/script/vendor/mathjax"
        console.log("   > new MathJax.Ajax.config.root => " + MathJax.Ajax.config.root) // ff does not know w.l.origin
        // 
        MathJax.Hub.Config({
            config: ["MMLorHTML.js"],
            extensions: ["tex2jax.js","mml2jax.js","MathEvents.js","MathZoom.js","MathMenu.js",
                "toMathML.js","TeX/noErrors.js","TeX/noUndefined.js","TeX/AMSmath.js","TeX/AMSsymbols.js"],
            jax: ["input/TeX","input/MathML","output/HTML-CSS","output/NativeMML"],
            tex2jax: { inlineMath: [["$","$"],["\\(","\\)"]] },
            menuSettings: {
                zoom: "Double-Click", mpContext: true, mpMouse: true
            },
            errorSettings: {
                message: ["[Math Error]"]
            }
        });
        MathJax.Hub.Configured() // bootstrapes mathjax.js lib
        console.log("EduZEN.configuredMathJax().. for further investigation, check out MathJax-object next line.")
        console.log(MathJax) // is here to learn more about mathjax
    })

    // calls the alternative REST creation method with customized JSON format
    function createContent() {
        var content = prompt('Aufgabeninhalt', 'x*2')
            topic = dm4c.restc.request('POST', '/eduzen/content/create', { name: "Aufgabe1", content: content })
        dm4c.show_topic(new Topic(topic), 'show', null, true)
    }

    // calls the server side increase method of the selected Example topic
    function increaseExample() {
        var url = '/example/increase/' + dm4c.selected_object.id
            topic = dm4c.restc.request('GET', url)
        dm4c.show_topic(new Topic(topic), 'show', null, true)
    }

    // register an additional create command
    dm4c.add_listener("post_refresh_create_menu", function(type_menu) {
        type_menu.add_separator()
        type_menu.add_item({ label: "Add Content", handler: createContent })
    })

    function topicLabelCompare(a, b) {
    // compare "a" and "b" in some fashion, and return -1, 0, or 1
        var nameA = a.label
        var nameB = b.label
        if (nameA < nameB) // sort string ascending
            return -1
        if (nameA > nameB)
            return 1
        return 0 //default return value (no sorting)
    }

})
