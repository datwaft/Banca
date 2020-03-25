/* Deposits and retires*/
function change() /*Change the state of the input "description" because only deposits require it*/
{
  
    var rad = document.getElementById("rad2");
    var des_input = document.getElementById("description");
    var name = document.getElementById("name");
    if(rad.checked == true)
    {
      des_input.value = "";
      name.value = "";
      des_input.disabled = true;
      name.disabled = true;
    }else
    {
      des_input.disabled = false;
      name.disabled = false;
    } 
}

function hide() /*Change the state of the input "description" because only deposits require it*/
{
  
    var rad = document.getElementById("id");
    var id = document.getElementById("ced_table");
    var ced = document.getElementById("number_table");
    var ced2 = document.getElementById("number_table_2");
    if(rad.checked == true)
    {
      ced.style.display = 'none';
      ced2.style.display = 'none';
      id.style.display = '';
    }else
    {
      ced.style.display = '';
      ced2.style.display = '';
      id.style.display = 'none';
    } 
}

/* Deposits and retires*/

/*Transfer interface for cashier*/

function hideOptions()
{
  var id = document.getElementById("id_box");
  var id_combo = document.getElementById("id_ac_box");
  var acc = document.getElementById("box_acc");
  var index = document.getElementById("trans_options").value;
  
  if(index == 'id')
  {
    id.style.display = '';
    id_combo.style.display = '';
    acc.style.display = 'none';
  } else {
      id.style.display = 'none';
      id_combo.style.display = 'none';
      acc.style.display = 'table-row';
  }

//  }
}


/*Ends*/