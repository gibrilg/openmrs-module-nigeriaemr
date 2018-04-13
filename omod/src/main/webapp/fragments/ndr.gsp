<%
    def id = config.id
%>
<%= ui.resourceLinks() %>

<script>
    jq = jQuery;

    jq(function() {
        jq('#${ id }_button').click(function() {

            jq.getJSON('${ ui.actionLink("generateNDRFile") }',
                {
                    'start': '${ config.start }',
                    'end': '${ config.end }',
                })
                .success(function(filename) {
                    //alert(filename)
                    window.location = filename;
                })
                .error(function(xhr, status, err) {
                    alert('AJAX error ' + err);
                })
        });
    });
</script>



<a id="${ id }_button"  class="button app big" style="font-size:12px;min-height: 10px;">
    <i class="icon-file-alt"></i>
    <br/>
    <p>Generate NDR Report</p>
</a>
