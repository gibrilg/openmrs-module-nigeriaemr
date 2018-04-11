<%

    def id = config.id
    def props = config.properties ?: ["encounterType", "encounterDatetime", "location", "provider"]
%>
<%= ui.resourceLinks() %>

<script>
    jq = jQuery;

    jq(function() {
        jq('#${ id }_button').click(function() {
            jq.getJSON('${ ui.actionLink("getRADETReport") }',
                {
                    'start': '${ config.start }',
                    'end': '${ config.end }',
                    'properties': [ <%= props.collect { "'${it}'" }.join(",") %> ]
                })
                .success(function(data) {

                    alert('generating RADET was successful')
                })
                .fail(function(xhr, status, err) {
                    alert('AJAX error ' + err);
                })
        });
    });
</script>
 


<a id="${ id }_button"  class="button app big">
    <i class="icon-list-alt"></i>
    Generate RADET Report
</a>
