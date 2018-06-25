<% ui.decorateWith("appui", "standardEmrPage") %>

<%= ui.resourceLinks() %>

<form onsubmit="return false">
    <input id="patient-search" placeholder="Search by ID or Name" autocomplete="off" type="text">
</form>
<input type="button" value="Search" class="confirm button" onclick="Search()">
<br />
<br />
<div id="patient-search-results" style="display: block;">
    <div id="patient-search-results-table_wrapper" class="dataTables_wrapper" role="grid">
        <table id="patient-search-results-table" class="dataTable" aria-describedby="patient-search-results-table_info">
            <thead>
            <tr role="row">
                <th  style="width: 100px;"></th>
                <th class="ui-state-default" role="columnheader" style="width: 219px;">
                    <div class="DataTables_sort_wrapper">Identifier</div></th>
                <th class="ui-state-default" role="columnheader" style="width: 151px;">
                    <div class="DataTables_sort_wrapper">Name</div></th>
                <th class="ui-state-default" role="columnheader" style="width: 177px;">
                    <div class="DataTables_sort_wrapper">Gender</div></th>
                <th class="ui-state-default" role="columnheader" style="width: 105px;">
                    <div class="DataTables_sort_wrapper">Age</div></th>
            </tr>
            </thead>
            <tbody id="tblSearch" aria-live="polite" aria-relevant="all"></tbody>
        </table>
    </div>
</div>

<table>
    <tr>
        <td>
            <img id="LEFT_THUMB" border="1"  onclick="captureFP(0)" alt="LEFT THUMB" height=200 width=150 src=""> <br>
            <input type="button" value="Scan" id="BTN_LEFT_THUMB" onclick="captureFP(1)">
        </td>
        <td>
            <img id="LEFT_INDEX" border="1" alt="LEFT INDEX" height=200 width=150 src=""> <br>
            <input type="button" value="Scan" id="BTN_LEFT_INDEX" onclick="captureFP(2)">
        </td>
        <td>
            <img id="LEFT_MIDDLE" border="1" alt="LEFT MIDDLE" height=200 width=150 src=""> <br>
            <input type="button" value="Scan" id="BTN_LEFT_MIDDLE" onclick="captureFP(3)">
        </td>
        <td>
            <img id="LEFT_RING" border="1" alt="LEFT RING" height=200 width=150 src=""> <br>
            <input type="button" value="Scan" id="BTN_LEFT_RING" onclick="captureFP(4)">
        </td>
        <td>
            <img id="LEFT_LITTLE" border="1" alt="LEFT LITTLE" height=200 width=150 src=""> <br>
            <input type="button" value="Scan" id="BTN_LEFT_LITTLE" onclick="captureFP(5)">
        </td>
    </tr>
    <tr>
        <td>
            <p id="LEFT_THUMB_INFO"> </p>
        </td>
        <td>
            <p id="LEFT_INDEX_INFO"> </p>
        </td>
        <td>
            <p id="LEFT_MIDDLE_INFO"> </p>
        </td>
        <td>
            <p id="LEFT_RING_INFO"> </p>
        </td>
        <td>
            <p id="LEFT_LITTLE_INFO"> </p>
        </td>
    </tr>
    <tr>
        <td>
            <img id="RIGHT_THUMB" border="1"  alt="RIGHT THUMB" height=200 width=150 src=""> <br>
            <input type="button" value="Scan" id="BTN_RIGHT_THUMB" onclick="captureFP(6)">
        </td>
        <td>
            <img id="RIGHT_INDEX" border="1" alt="RIGHT INDEX" height=200 width=150 src=""> <br>
            <input type="button" value="Scan" id="BTN_RIGHT_INDEX" onclick="captureFP(7)">
        </td>
        <td>
            <img id="RIGHT_MIDDLE" border="1" alt="RIGHT MIDDLE" height=200 width=150 src=""> <br>
            <input type="button" value="Scan" id="BTN_RIGHT_MIDDLE" onclick="captureFP(8)">
        </td>
        <td>
            <img id="RIGHT_RING" border="1" alt="RIGHT RING" height=200 width=150 src=""> <br>
            <input type="button" value="Scan" id="BTN_RIGHT_RING" onclick="captureFP(9)">
        </td>
        <td>
            <img id="RIGHT_LITTLE" border="1" alt="RIGHT LITTLE" height=200 width=150 src=""> <br>
            <input type="button" value="Scan" id="BTN_RIGHT_LITTLE" onclick="captureFP(10)">
        </td>
    </tr>
    <tr>
        <td>
            <p id="RIGHT_THUMB_INFO"> </p>
        </td>
        <td>
            <p id="RIGHT_INDEX_INFO"> </p>
        </td>
        <td>
            <p id="RIGHT_MIDDLE_INFO"> </p>
        </td>
        <td>
            <p id="RIGHT_RING_INFO"> </p>
        </td>
        <td>
            <p id="RIGHT_LITTLE_INFO"> </p>
        </td>
    </tr>
</table>
<br />
<div >
    <input type="button" value="Reset" onclick="location.reload(true);">
    <input type="button" value="Save" class="confirm button" onclick="Save()">
    <br>
</div>

<script type="text/javascript">
    let patientId;
    let newPrint;
    let capturedPrint = [];
    let	fingerPosition = ["","LEFT_THUMB", "LEFT_INDEX", "LEFT_MIDDLE", "LEFT_RING", "LEFT_LITTLE",
        "RIGHT_THUMB", "RIGHT_INDEX", "RIGHT_MIDDLE", "RIGHT_RING", "RIGHT_LITTLE" ];
    let url = 'http://localhost:2018/api/FingerPrint';

    function captureFP( position ){

        if(patientId === undefined){
            alert('Select a patient first');
            return;
        }

         let captureURL =url + '/CapturePrint?fingerPosition='+position;

        jQuery.getJSON(captureURL)
            .success(function(data) {
                if(data.ErrorMessage === ''  || data.ErrorMessage === null){
                    let	imgId = fingerPosition[position];
                    document.getElementById(imgId).src = "data:image/bmp;base64," + data.Image;
                    newPrint = data;
                    newPrint.PatienId = patientId;
                    newPrint.Image ='';
                    capturedPrint.push(newPrint);
                }
                else{
                    alert(data.ErrorMessage);
                }
            })
            .error(function(xhr, status, err) {
                alert('AJAX error ' + err);
            });
    }

    function Save(){

       let  saveUrl = url + '/SaveToDatabase';

        jQuery.ajax({
            type: "Post",
            url: saveUrl,
            contentType: "application/json; charset=utf-8",
            data : JSON.stringify(capturedPrint),
            cache: false,
        }).done(function (response) {
            alert(response.ErrorMessage);
        }) .error(function(xhr, status, err) {
            alert(err);
            console.log(err);
        });
    }

    function Search(){

        let identifier = jQuery('#patient-search').val();
        let searchUrl = '/openmrs/ws/rest/v1/patient?identifier=' +identifier +
            '&v=custom:(patientId,uuid,patientIdentifier:(uuid,identifier),person:(gender,age,birthdate,birthdateEstimated,personName),attributes:(value,attributeType:(name)))'


        jQuery.getJSON(searchUrl)
            .success(function(data){
                jQuery("#tblSearch").empty();
                jQuery.each(data.results, function(i,v){
                    let tbl_Id = v.patientIdentifier.identifier;
                    let tbl = '<tr><td><input name="rpt" class="chcktblpt" type="radio" id='+v.patientId  +' class="chcktbl"></td>';
                    tbl += '<td>'+ tbl_Id + '</td>';
                    tbl += '<td>'+ v.person.personName.display + '</td>';
                    tbl += '<td>'+ v.person.gender + '</td>';
                    tbl += '<td>'+ v.person.age + '</td>';
                    tbl += '</tr>';

                    jQuery("#tblSearch").append(tbl);
                });
            }).error(function (xhr, status,err) {
            alert('error ' + err);
        });
    }

    jQuery(document).on('click', '.chcktblpt', function (e) {
        patientId = this.id;
        newPrint ={};
        capturedPrint = [];
    });

</script>
