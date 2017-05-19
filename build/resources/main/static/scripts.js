

var buttonTextStates = ['Show panel', 'Hide panel'];
var tableRowIndexPrefix = '#line';
var styleBlock = 'block';
var styleNone = 'none';
var formChangeDishId = '#changedish';
var buttonChangeDishPanelId = '#togglebutton';

var urls = {
    listWeb : '/dishes',
    updateApi : '/app/api/update/dish',
    deleteApi : '/app/api/delete/dish'
}

var queryParams = {
    name : 'name',
    weight : 'weight',
    type : 'type',
    cost : 'cost',
    currency : 'currency',
    ingredients : 'ingredients',
    bitmapUrl : 'bitmapurl',
    description : 'description'
}

var querySign = '?';
var equalSign = '=';
var isUpdate = false;

var formIds = {
    name : "#name",
    weight : "#weight",
    type : "#type",
    cost : "#cost",
    currency : "#currency",
    ingredients : "#ingredients",
    bitmapUrl : "#bitmapUrl",
    description : "#description",
    isUpdate : "#isupdate"
}

var error = {
    id : 'errorresponse',
    class : 'font24 error',
    response : '{\"response\":{\"error\":\"Some error encountered during action\"}}'
}

function performRequest() {
    if(isUpdate){
        $(formIds.isUpdate)[0].value = 'true';
    } else {
        $(formIds.isUpdate)[0].value = 'false';
    }
    document.dishform.submit();
}

function formReset() {
    isUpdate = false;
    var nameInput = $(formIds.name)[0];
    var weightInput = $(formIds.weight)[0];
    var typeInput = $(formIds.type)[0];
    var costInput = $(formIds.cost)[0];
    var currencyInput = $(formIds.currency)[0];
    var ingredientsInput = $(formIds.ingredients)[0];
    var bitmapUrlInput = $(formIds.bitmapUrl)[0];
    var descriptionInput = $(formIds.description)[0];
    var EMPTY = '';
    nameInput.value = EMPTY;
    weightInput.value = EMPTY;
    typeInput.value = EMPTY;
    costInput.value = EMPTY;
    currencyInput.value = EMPTY;
    ingredientsInput.value = EMPTY;
    bitmapUrl.value = EMPTY;
    description.value = EMPTY;
}

function updateDishPreload(index){
    isUpdate = true;
    var nameInput = $(formIds.name)[0];
    var weightInput = $(formIds.weight)[0];
    var typeInput = $(formIds.type)[0];
    var costInput = $(formIds.cost)[0];
    var currencyInput = $(formIds.currency)[0];
    var ingredientsInput = $(formIds.ingredients)[0];
    var bitmapUrlInput = $(formIds.bitmapUrl)[0];
    var descriptionInput = $(formIds.description)[0];
    nameInput.readOnly = true;
    nameInput.value = $(formIds.name + index)[0].innerText;
    weightInput.value = $(formIds.weight + index)[0].innerText;
    typeInput.value = $(formIds.type + index)[0].innerText;
    costInput.value = $(formIds.cost + index)[0].innerText;
    currencyInput.value = $(formIds.currency + index)[0].innerText;
    ingredientsInput.value = $(formIds.ingredients + index)[0].innerText;
    bitmapUrlInput.value = $(formIds.bitmapUrl + index)[0].innerText;
    descriptionInput.value = $(formIds.description + index)[0].innerText;
    toggle(true);
}

function deleteDishAjax(index, name){
    $.ajax({
        url: urls.deleteApi + querySign + queryParams.name + equalSign + name,
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

function toggle(show) {
    var e = $(formChangeDishId)[0];
    var button = $(buttonChangeDishPanelId)[0];
    var display = function() {
            e.style.display = styleNone;
            button.innerText = buttonTextStates[0];
        };
    var hide = function() {
        e.style.display = styleBlock;
        button.innerText = buttonTextStates[1];
    };
    if(show){
        display();
    }
    if(e.style.display == styleBlock){
        display();
    } else {
        hide();
    }
}

window.onload = function() {
    $(formChangeDishId).hide();
    $(buttonChangeDishPanelId)[0].innerText = buttonTextStates[0];
    formReset();
}