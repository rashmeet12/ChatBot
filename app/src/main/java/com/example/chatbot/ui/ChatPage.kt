package com.example.chatbot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatbot.R
import com.example.chatbot.ui.theme.ColorModelMessage
import com.example.chatbot.ui.theme.ColorUserMessage
import com.example.chatbot.ui.theme.TextFieldColor
import com.example.chatbot.ui.theme.backgroundColor
import com.example.chatbot.ui.theme.headerColor

@Composable
fun ChatPage(modifier: Modifier = Modifier, viewModel: ChatViewModel){
    Box(modifier = Modifier.fillMaxSize().background(color = backgroundColor)) {
        Column(
            modifier=modifier
        ) {
            AppHeader()
            MessageList( modifier = Modifier.weight(1f),messageList = viewModel.messageList )
            MessageInput(onMessageSend = {
                viewModel.sendMessage(it)

            })

        }
    }

}

@Composable
fun MessageList(modifier: Modifier,messageList:List<MessageModel>){
    if(messageList.isEmpty()){
        Column (modifier=modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

            ){
            Icon(modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.baseline_question_answer_24), contentDescription = "Icon",
                tint = White)
            Text(text = "Ask Me Anything", fontSize = 22.sp)
        }
    }
    LazyColumn(modifier = modifier,
        reverseLayout = true){
        items(messageList.reversed()){
            MessageRow(messageModel = it)
        }
    }
}

@Composable
fun MessageRow(messageModel:MessageModel){
    val isModel=messageModel.role=="model"
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(modifier = Modifier.fillMaxWidth()){
            Box(modifier = Modifier
                .align(
                    if (isModel) Alignment.BottomStart
                    else Alignment.BottomEnd
                )
                .padding(
                    start = if (isModel) 8.dp else 70.dp,
                    end = if (isModel) 70.dp else 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .clip(RoundedCornerShape(68f))
                .background(if (isModel) ColorModelMessage else ColorUserMessage)
                .padding(16.dp)
             )
            {
                SelectionContainer {
                    Text(text = messageModel.message, fontWeight = FontWeight.W500,
                        color = Color.White)
                }

            }

        }
    }
}

@Composable
fun AppHeader(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = headerColor)
        .shadow(elevation = 3.dp))
    {
        Text(modifier = Modifier.padding(16.dp).align(Alignment.Center), text = "Chat with AI",
            color = Color.Cyan,
            fontSize = 26.sp)
    }
}


@Composable
fun MessageInput(onMessageSend:(String)->Unit){
    var message by remember {
        mutableStateOf("")
    }
    Row(modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically){
        OutlinedTextField(modifier = Modifier.weight(1f).background(color = TextFieldColor)
            ,value =message
            , onValueChange ={
            message=it
        },
            placeholder = { Text(text = "Ask Your Question")},
            shape = RoundedCornerShape(10.dp) )
        IconButton(onClick = {
            if(message.isNotEmpty()){
                onMessageSend(message)
                message=""
            }
        }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription ="Send" )

        }
    }

}