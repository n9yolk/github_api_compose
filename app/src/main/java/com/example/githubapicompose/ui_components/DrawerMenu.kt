package com.example.githubapicompose.ui_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.example.githubapicompose.R
import com.example.githubapicompose.utils.DrawerEvents

@Composable
fun DrawerMenu(onEvent: (DrawerEvents) -> Unit) {
    val list = stringArrayResource(id = R.array.drawer_list)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color.DarkGray)
        ) {
            Text(
                text = "GitHub API",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

       LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(list){index, title ->
                MenuParts(title = title){ event ->
                    onEvent(event)
                }
            }
        }
    }

}

@Composable
fun MenuParts(title: String, onEvent: (DrawerEvents) -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(DrawerEvents.OnItemClick(title))
                }
                .padding(10.dp)
                .wrapContentWidth()
        )
    }
}