dm4c.add_plugin("de.tu-berlin.eduzen", function() {

    // define type specific commands and register them
    dm4c.add_listener('init', function (e) {})

    /** calls the alternative REST creation method with customized JSON format
    function createContent() {
        var content = prompt('Aufgabeninhalt', 'x*2')
            topic = dm4c.restc.request('POST', '/eduzen/resource/create', { name: "Aufgabe1", content: content })
        dm4c.show_topic(new Topic(topic), 'show', null, true)
    } **/

    /** calls the server side increase method of the selected Example topic
    function increaseExample() {
        var url = '/example/increase/' + dm4c.selected_object.id
            topic = dm4c.restc.request('GET', url)
        dm4c.show_topic(new Topic(topic), 'show', null, true)
    } **/

    /**register an additional create command
    dm4c.add_listener("post_refresh_create_menu", function(type_menu) {
        type_menu.add_separator()
        type_menu.add_item({ label: "Add Content", handler: createContent })
    }) **/

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
