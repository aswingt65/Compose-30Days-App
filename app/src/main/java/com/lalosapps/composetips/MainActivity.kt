package com.lalosapps.composetips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lalosapps.composetips.data.TipRepository
import com.lalosapps.composetips.model.Tip
import com.lalosapps.composetips.ui.theme.composetipsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            composetipsTheme {
                KotlinApp()
            }
        }
    }
}

@Composable
fun KotlinApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar()
        }
    ) {
        TipList(tips = TipRepository.tips, Modifier.padding(it))
    }
}

@Composable
fun TopAppBar() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
fun TipItem(tip: Tip, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )
    Card(
        modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .background(color = color)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(id = tip.dayRes),
                        style = MaterialTheme.typography.h2
                    )
                    Text(
                        text = stringResource(id = tip.titleRes),
                        style = MaterialTheme.typography.body1
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        tint = MaterialTheme.colors.secondary,
                        contentDescription = stringResource(id = R.string.expand_button_content_description)
                    )
                }
            }
            if (expanded) {
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        bottom = 16.dp,
                        end = 16.dp
                    )
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(194.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        painter = painterResource(id = tip.imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = tip.descRes),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Composable
fun TipList(tips: List<Tip>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
    ) {
        items(tips) {
            TipItem(tip = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    composetipsTheme {
        KotlinApp()
    }
}