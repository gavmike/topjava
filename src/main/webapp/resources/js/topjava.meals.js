// $(document).ready(function () {

$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "Date"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },

                    {
                        "defaultContent": "Update",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
filter();
resetFilter();
});



function filter(){
    $("#filterButton").click(function(){
        $.ajax({
            type: "GET",
            url: "ajax/meals/filter",
            data: $('#filterForm').serialize()
        }).done(function () {

            updateTable();
            successNoty("Filtered");
        });
    });
}



function resetFilter() {
    $("#resetButton").click(function () {
      $("#filterForm").each(function () {
          $(this).find(":input").val("");
      });
        $("#filterButton").click();

        });

}

