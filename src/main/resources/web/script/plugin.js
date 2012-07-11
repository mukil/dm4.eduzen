dm4c.add_plugin("eduzen_plugin", function() {

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
