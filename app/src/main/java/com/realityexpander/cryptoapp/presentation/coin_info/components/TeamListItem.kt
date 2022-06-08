package com.realityexpander.cryptoapp.presentation.coin_info.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.realityexpander.cryptoapp.data.remote.dto.TeamMember

//@Suppress("FunctionName")  // remove capitalization warning
//fun LazyListScope.TeamMembersList(teamMembers: List<TeamMember>) {
@Composable
fun TeamMembersList(teamMembers: List<TeamMember>) {
//    items(teamMembers) { member ->
    teamMembers.forEach { member ->
        TeamListItem(
            member,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
        Divider()
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
private fun TeamListItem(
    teamMember: TeamMember,
    modifier: Modifier = Modifier
) {
    Text(
        text = teamMember.name,
        modifier = Modifier
            .padding(bottom = 5.dp),
        style = MaterialTheme.typography.h4,
    )
    Text(
        text = teamMember.position,
        modifier = modifier,
        style = MaterialTheme.typography.body2,
        fontStyle = FontStyle.Italic
    )
}