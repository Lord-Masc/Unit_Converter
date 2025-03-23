package com.example.unitconvertor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import com.example.unitconvertor.ui.theme.UnitConvertorTheme

// Data class to store unit name and conversion factor
data class UnitItem(val name: String, val conversionFactor: Double)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConvertorTheme {
                Surface {
                    UnitConvertor()
                }
            }
        }
    }
}

@Composable
fun UnitConvertor() {
    // Define available units and their conversion factors
    val units = listOf(
        UnitItem("Meter", 1.0),
        UnitItem("Centimeter", 0.01),
        UnitItem("Feet", 0.3048),
        UnitItem("Inches", 0.0254),
        UnitItem("Yard", 0.9144),
        UnitItem("Millimeter", 0.001)
    )

    // State variables for input and output fields
    var inputValue by remember { mutableStateOf("") }
    var selectedInputUnit by remember { mutableStateOf(units[0]) }
    var selectedOutputUnit by remember { mutableStateOf(units[0]) }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text("Unit Converter", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(20.dp))

        UnitField(
            label = "Input",
            selectedUnit = selectedInputUnit,
            value = inputValue,
            onValueChange = { inputValue = it },
            onUnitChange = { selectedInputUnit = it },
            units = units
        )

        Spacer(modifier = Modifier.height(20.dp))

        UnitField(
            label = "Output Unit",
            selectedUnit = selectedOutputUnit,
            value = result,
            onValueChange = {},
            onUnitChange = { selectedOutputUnit = it },
            units = units,
            isEditable = false
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            result = convertUnits(inputValue, selectedInputUnit, selectedOutputUnit)
        }) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (result.isNotEmpty()) {
            Text("Result: $result", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun UnitField(
    label: String,
    selectedUnit: UnitItem,
    value: String,
    onValueChange: (String) -> Unit,
    onUnitChange: (UnitItem) -> Unit,
    units: List<UnitItem>,
    isEditable: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        FilledTonalButton(
            onClick = { expanded = true },
            modifier = Modifier.width(140.dp)
        ) {
            Text(selectedUnit.name)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Unit")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit.name) },
                    onClick = {
                        onUnitChange(unit)
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedTextField(
            value = value,
            onValueChange = { if (isEditable) onValueChange(it) },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = isEditable
        )
    }
}

fun convertUnits(value: String, fromUnit: UnitItem, toUnit: UnitItem): String {
    return try {
        val inputValue = value.toDouble()
        val result = inputValue * fromUnit.conversionFactor / toUnit.conversionFactor
        "%.2f".format(result)
    } catch (e: Exception) {
        "Invalid input"
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUnitConvertor() {
    UnitConvertorTheme {
        UnitConvertor()
    }
}
