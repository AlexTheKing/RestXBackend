

var buttonTextStates = ['Show panel', 'Hide panel'];
var tableRowIndexPrefix = '#line';
var styleBlock = 'block';
var styleNone = 'none';
var formChangeDishId = '#changedish';
var buttonChangeDishPanelId = '#togglebutton';

var urls = {
    listWeb = '/dishes';
    updateApi = '/app/api/update/dish';
    deleteApi = '/app/api/delete/dish';
}

var queryParams = {
    name = 'name';
    weight = 'weight';
    type = 'type';
    cost = 'cost';
    currency = 'currency';
    ingredients = 'ingredients';
    bitmapUrl = 'bitmapurl';
    description = 'description';
}

var querySign = '?';
var isUpdate = false;

var formIds = {
    name = "#name";
    weight = "#weight";
    type = "#type";
    cost = "#cost";
    currency = "#currency";
    ingredients = "#ingredients";
    bitmapUrl = "#bitmapUrl";
    description = "#description";
    isUpdate = "#isupdate";
}

function performRequest() {
    if(isUpdate){
        $(formIds.isUpdate)[0].value = 'true';
    }
    document.dishform.submit();
}

function formReset() {
    isUpdate = false;
    document.dishform.reset();
}

function updateDishPreload(index){
    isUpdate = true;
    var tableRow = $(getTableId(index))[0];
    var cells = tableRow.childNodes;
    var nameInput = $(formIds.name)[0];
    var weightInput = $(formIds.weight)[0];
    var typeInput = $(formIds.type)[0];
    var costInput = $(formIds.cost)[0];
    var currencyInput = $(formIds.currency)[0];
    var ingredientsInput = $(formIds.ingredients)[0];
    var bitmapUrlInput = $(formIds.bitmapUrl)[0];
    var descriptionInput = $(formIds.description)[0];
    nameInput.readonly = true;
    nameInput.value = cells[0].innerText;
    weightInput.value = cells[1].innerText;
    typeInput.value = cells[2].innerText;
    costInput.value = cells[3].innerText;
    currencyInput.value = cells[4].innerText;
    ingredientsInput.value = cells[5].innerText;
    bitmapUrlInput.value = cells[6].innerText;
    descriptionInput.value = cells[7].innerText;
}

function deleteDishAjax(index, name){
    $.ajax({
        url: urls.deleteApi + querySign + queryParams.name + name,
        success: function(result) {
            if(result == error.response){
                $(error.id).attr('class', error.class);
            } else {
                $(getTableId(index)).hide();
            }
        }
    });
}

function getTableId(index){
    return tableRowIndexPrefix + index;
}

function toggle(idbutton, showhide) {
    var e = $(formChangeDishId)[0];
    var button = $(idbutton)[0];
    if(e.style.display == styleBlock){
        e.style.display = styleNone;
        button.innerText = showhide[1];
    } else {
        e.style.display = styleBlock;
        button.innerText = showhide[0];
    }
}

window.onload = function() {
    $(formChangeDishId).hide();
    $(buttonChangeDishPanelId)[0].innerText = buttonTextStates[0];
    isUpdate = false;
}