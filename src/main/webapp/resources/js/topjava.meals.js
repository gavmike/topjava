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

});



/*function filter(){
    $("#filterButton").click(function(){
        $.ajax({
            type: "GET",
            url: "ajax/meals/filter",
            data: $('#filterForm').serialize()
        }).done(function (data) {
            $("#editRow").modal('hide');

            //datatableApiMeal.clear().rows.add(data).draw();
            tab.clear().rows.add(data).draw();
           /!* updateTable();
            successNoty("Filtered");*!/
        });
    });
}*/

function filter() {
    $.get(context.ajaxUrl+"filter",$('#filterForm').serialize())
        .done(function (data) {
            context.datatableApi.clear().rows.add(data).draw()});
}


/*function resetFilter() {
    $("#resetButton").click(function () {
      $("#filterForm").each(function () {
          $(this).find(":input").val("");
      });
        $("#filterButton").click();

        });

}*/

