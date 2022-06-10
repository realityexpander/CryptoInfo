package com.realityexpander.cryptoapp.presentation.coin_info

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import com.realityexpander.cryptoapp.common.roundToDecimalPlaces
import com.realityexpander.cryptoapp.presentation.coin_info.components.CoinTags
import com.realityexpander.cryptoapp.presentation.coin_info.components.TeamMembersList

@Composable
fun CoinInfoScreen(
    navController: NavController,
    viewModel: CoinInfoViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    // Shows image as a backdrop
    AsyncImage(
        model = "file:///android_asset/facility_photo_1.jpg",
        contentScale = ContentScale.FillHeight,
        contentDescription = "Facility Photo",
        modifier = Modifier.fillMaxHeight()
    )

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp)
        ) {

            item {
                if (state.isError) {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }

                if (!state.isLoading) {
                    if (state.coinInfo.coinId.isNotEmpty()) { // check empty instead of null for better UX
                        val coinInfo = state.coinInfo

                        // Basic info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween, // uses weights
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Back arrow button
                            IconButton(
                                onClick = { navController.navigateUp() },
                                modifier = Modifier
                                    .padding(start = 0.dp, end = 5.dp)
                                    .weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    tint = MaterialTheme.colors.onBackground,
                                    contentDescription = "Back",
                                )
                            }

                            // Coin Name & Active
                            Text(
                                text = "${coinInfo.rank + 1}. ${coinInfo.name} (${coinInfo.symbol})",
                                style = MaterialTheme.typography.h2,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.weight(8f)  // out of 10f
                            )
                            Text(
                                text = if (coinInfo.isActive) "Active" else "Inactive",
                                color = if (coinInfo.isActive) Color.Green else MaterialTheme.colors.error,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .align(CenterVertically)
                                    .weight(2f)  // out of 10f
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))

                        // Current Price (Loaded async from info)
                        coinInfo.ohlcvToday?.let {
                            Text(
                                text = "Price \$${
                                    coinInfo.ohlcvToday
                                        .close
                                        .roundToDecimalPlaces(4)
                                }",
                                style = MaterialTheme.typography.h2,
                                textAlign = TextAlign.Left,
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))

                        // Description
                        Text(
                            text = coinInfo.description,
                            style = MaterialTheme.typography.body2,
                        )
                        Spacer(modifier = Modifier.height(15.dp))


                        // Tags
                        Text(
                            text = "Tags:",
                            style = MaterialTheme.typography.h3
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        FlowRow(
                            mainAxisSpacing = 10.dp,
                            crossAxisSpacing = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CoinTags(coinInfo.tags)
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Divider()

                        Text(
                            text = "Web resources:",
                            style = MaterialTheme.typography.h3
                        )
                        LaunchWebsiteButton(
                            coinInfo.linkCatalog.websites.firstOrNull()
                                ?: coinInfo.linkCatalog.facebooks.firstOrNull()
                                ?: coinInfo.linkCatalog.reddits.firstOrNull()
                                ?: "",
                            LocalContext.current
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Divider()

                        // Team Members header
                        Text(
                            text = "Team members:",
                            style = MaterialTheme.typography.h3
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        TeamMembersList(coinInfo.teamMembers)

                    }
                }
            }
        }
    }
}

@Composable
fun LaunchWebsiteButton(website: String, context: Context) {
    if (website.isEmpty()) {
        return
    }

    ClickableText(
        with(AnnotatedString.Builder()) {
            pushStyle(
                SpanStyle(
                    color = MaterialTheme.colors.onBackground,
                    textDecoration = TextDecoration.Underline,
                    fontStyle = FontStyle.Italic
                )
            )
            append(
                "${
                    website.substringAfter("://")
                        .replace("www.", "")
                }"
            )
            toAnnotatedString()
        },
        style = MaterialTheme.typography.body2,
        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
        onClick = { launchWebUrl(website, context) },
    )

    // alternate but more simple method
//    TextButton(onClick = { launchWebUrl(website, context) }) {
//        Text(
//            text = "$website",
//            style = MaterialTheme.typography.body2,
//            color = MaterialTheme.colors.onBackground,
//        )
//    }
}

fun launchWebUrl(url: String?, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(if (!url.isNullOrEmpty()) url else "https://www.google.com")
    startActivity(context, intent, null)
}