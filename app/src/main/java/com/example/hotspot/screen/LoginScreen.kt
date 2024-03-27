package com.example.hotspot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hotspot.R
import com.example.hotspot.ui.theme.HotSpotTheme




@Composable
fun LoginScree() {
    Surface{
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box (
                contentAlignment = Alignment.TopCenter
            ){
                /*Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight(fraction = 0.46f),
                    painter = painterResource(id = R.drawable.shape),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                    )*/
            }
            Row (
                modifier = Modifier.padding(top= 80.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    modifier = Modifier.size(42.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Trouve ton lieux"
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(text = "Hotspot")
                }
            }
        }
    }

}

@Preview
@Composable
fun LoginScreenPreview() {
    HotSpotTheme {
        LoginScree()
    }
}