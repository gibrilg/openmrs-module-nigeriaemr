<%
    def id = config.id
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
                })
                .success(function(data) {
                    //alert(data)
                    window.location = data;
                })
                .error(function(xhr, status, err) {
                    alert('AJAX error ' + err);
                })
        });
    });
</script>
 


<a id="${ id }_button"  class="button app big" style="font-size:12px;min-height: 10px;">
    <i class="icon-list-alt"></i>
    <br/>
    Generate RADET Report
</a>
