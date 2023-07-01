package com.rubenpla.composetweetdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rubenpla.composetweetdemo.ui.theme.ComposeTweetDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTweetDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF171c28)
                ) {
                    TweetReplication()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(
    name = "TWEET PREVIEW",
    showBackground = true,
    showSystemUi = true,
    apiLevel = 33,
    device = Devices.PIXEL_3A_XL
)
@Composable
fun GreetingPreview() {
    ComposeTweetDemoTheme {
        TweetReplication()
    }
}

@Composable
fun TweetReplication() {
    Column(
        modifier = Modifier
            .background(Color(0xFF171c28))
    ) {
        TweetHeader()
    }
}

@Composable
fun TweetHeader() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        //.background(Color.DarkGray)
    ) {
        val (avatarPhoto, tweetHeader, tweetDescription, tweetImage, footer, divider) = createRefs()
        val avatarPhotoBarrier = createEndBarrier((avatarPhoto))
        val userInfoBarrier = createBottomBarrier(tweetHeader)

        Image(
            painterResource(id = R.drawable.profile),
            contentDescription = "profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .constrainAs(avatarPhoto) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp, start = 16.dp)
            .constrainAs(tweetHeader) {
                start.linkTo(avatarPhotoBarrier)
                // end.linkTo(parent.end)
                top.linkTo(avatarPhoto.top)
            }) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (avatarName, userName, timestamp, dots_icon) = createRefs()

                Text(
                    text = "AvatarName",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .constrainAs(avatarName) {
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = "@UserName",
                    color = Color.Gray,
                    fontSize = 19.sp,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .constrainAs(userName) {
                            start.linkTo(avatarName.end)
                        }
                )
                Text(text = "4h", color = Color.Gray,
                    fontSize = 19.sp,
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .constrainAs(timestamp) {
                            start.linkTo(userName.end)
                        })
                Icon(
                    painter = painterResource(id = R.drawable.ic_dots),
                    contentDescription = "ic_dots",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 48.dp).constrainAs(dots_icon) {
                        end.linkTo(parent.end)
                    }
                )
            }

        }

        Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
                "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla " +
                "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa" +
                " qui officia deserunt mollit anim id est laborum.",
            maxLines = 5,
            color = Color.White,
            fontSize = 19.sp,
            modifier = Modifier
                .padding(top = 2.dp, start = 16.dp, end = 48.dp)
                .constrainAs(tweetDescription) {
                    start.linkTo(tweetHeader.start)
                    end.linkTo(tweetHeader.end)
                    top.linkTo(userInfoBarrier)
                })

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentScale = ContentScale.Crop,
            contentDescription = "Tweet Body Image",
            modifier = Modifier
                .height(250.dp)
                .widthIn(min = 100.dp, max = 335.dp)
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(18.dp))
                .constrainAs(tweetImage) {
                    top.linkTo(tweetDescription.bottom)
                    // start.linkTo(tweetDescription.start)
                    end.linkTo(parent.end)
                }
        )

        Row(modifier = Modifier
            .padding(top = 24.dp, start = 16.dp)
            .constrainAs(footer) {
                start.linkTo(tweetDescription.start)
                top.linkTo(tweetImage.bottom)
            }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chat),
                    contentDescription = "footer comment icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )

                Text(text = "1", modifier = Modifier.padding(8.dp), color = Color.Gray)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 64.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_rt),
                    contentDescription = "footer comment icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )

                Text(text = "1", modifier = Modifier.padding(8.dp), color = Color.Gray)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 64.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_like),
                    contentDescription = "footer comment icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )

                Text(text = "1", modifier = Modifier.padding(8.dp), color = Color.Gray)
            }

        }
    }
    Divider(
        thickness = 0.8.dp,
        color = Color.LightGray,
        modifier = Modifier
    )
}