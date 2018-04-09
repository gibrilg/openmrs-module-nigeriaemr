<% ui.decorateWith("appui", "standardEmrPage") %>


${ ui.includeFragment("nigeriaemr", "ndr",
        [   start: "2011-02-16",
            end: "2011-02-16 23:59:59.999",
            properties: ["location", "encounterDatetime"],
        ]) }

