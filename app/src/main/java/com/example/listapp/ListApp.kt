package com.example.listapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listapp.ui.theme.ListAppTheme


//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listApp(){
    var sItem by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // It's is for to Add list in app
        Button(onClick = {
           showDialog = true
        }, modifier = Modifier.padding(top = 32.dp)) {
            Text("Add")
            Icon(Icons.Default.Add, contentDescription = "Add List")
        }
        LazyColumn(
            modifier = Modifier.padding( 16.dp)
        ) {
          item(sItem){

          }
        }
    }
    if (showDialog){
        AlertDialog(onDismissRequest = { showDialog = false },confirmButton = {

        }, title = {Text("Add Shopping Item")},
            text = {
                Column() {
                    OutlinedTextField(value = itemName,
                        onValueChange = {itemName = it},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                        )
                    OutlinedTextField(value = itemQuantity,
                        onValueChange = {itemQuantity= it},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    )
                }
            })
    }

}
@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ListAppTheme {
        listApp()
    }
}
data class ShoppingItem(val id:Int,
                        val name:String ,
                        val quantity:Int ,
                        var isEditing: Boolean= false)

