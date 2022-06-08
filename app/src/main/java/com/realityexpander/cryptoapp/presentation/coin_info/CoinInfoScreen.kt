package com.realityexpander.cryptoapp.presentation.coin_info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import com.realityexpander.cryptoapp.presentation.coin_info.components.CoinTags
import com.realityexpander.cryptoapp.presentation.coin_info.components.TeamMembersList

@Composable
fun CoinInfoScreen(
    navController: NavController,
    viewModel: CoinInfoViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        state.coinInfo?.let { coinInfo ->

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {

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

                    // Team Members header
                    Text(
                        text = "Team members:",
                        style = MaterialTheme.typography.h3
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }

                TeamMembersList(coinInfo.teamMembers)
            }
        }

        if (state.isError) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

    }

}