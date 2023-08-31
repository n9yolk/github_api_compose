package com.example.githubapicompose.data

import android.app.ActionBar.Tab
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.githubapicompose.utils.HtmlLoader
import com.example.githubapicompose.utils.HtmlText
import com.example.githubapicompose.utils.MainViewModel
import org.json.JSONObject


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RepoCard(mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory),
             item: RepoModel, onClick: (String) -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .combinedClickable(
                onDoubleClick = {
                    mainViewModel.insertItem(
                        TableData(null, item.description, item.name, item.html_url, item.language)
                    )
                },
                onClick = {
                },
            ),
        shape = RoundedCornerShape(15.dp),
        elevation = 15.dp,

    ) {
        Box(
            modifier = Modifier.padding(10.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = item.name)
                    Text(text = item.description)
                    HtmlText(html = item.html_url, Modifier.clickable {
                        onClick(item.html_url)
                    })
                    Text(text = item.language)
                }

            }
        }
    }
}