package com.dookie.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DookieApp()
        }
    }
}

// Wacky color palette
val NeonPink = Color(0xFFFF006E)
val ElectricBlue = Color(0xFF00F5FF)
val SlimeGreen = Color(0xFF39FF14)
val WackyPurple = Color(0xFFBF00FF)
val HotOrange = Color(0xFFFF6B35)
val CyberYellow = Color(0xFFFFD700)

@Composable
fun DookieApp() {
    var currentScreen by remember { mutableStateOf(0) }
    
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            NeonPink.copy(alpha = 0.3f),
            ElectricBlue.copy(alpha = 0.2f),
            SlimeGreen.copy(alpha = 0.3f)
        )
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        when (currentScreen) {
            0 -> HomeScreen { currentScreen = it }
            1 -> VibeCheckScreen { currentScreen = 0 }
            2 -> BrainrotGenerator { currentScreen = 0 }
            3 -> RizzCalculator { currentScreen = 0 }
        }
    }
}

@Composable
fun HomeScreen(onNavigate: (Int) -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "float")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(40.dp))
            
            // Wacky title
            Text(
                text = "💩 dookie",
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraBold,
                color = NeonPink,
                textAlign = TextAlign.Center,
                modifier = Modifier.scale(scale)
            )
            
            Text(
                text = "for the zoomers 😈",
                fontSize = 18.sp,
                color = WackyPurple,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(40.dp))
        }
        
        val buttons = listOf(
            Triple("🧠 VIBE CHECK", "are you cooked?", NeonPink to HotOrange),
            Triple("🔥 BRAINROT", "gen alpha moments", ElectricBlue to WackyPurple),
            Triple("🎯 RIZZ CALC", "aura points fr", SlimeGreen to CyberYellow)
        )
        
        items(buttons) { (title, subtitle, colors) ->
            WackyButton(
                title = title,
                subtitle = subtitle,
                gradientColors = listOf(colors.first, colors.second),
                onClick = { onNavigate(buttons.indexOf(Triple(title, subtitle, colors)) + 1) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        item {
            Spacer(modifier = Modifier.height(20.dp))
            
            // Random chaos button
            var chaosRotation by remember { mutableStateOf(0f) }
            
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(CyberYellow, HotOrange, NeonPink)
                        )
                    )
                    .clickable { 
                        chaosRotation += 360f
                    }
                    .rotate(chaosRotation),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "CHAOS\nMODE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "built different since 2024 🔥",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun WackyButton(
    title: String,
    subtitle: String,
    gradientColors: List<Color>,
    onClick: () -> Unit
) {
    val scale = remember { Animatable(1f) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .scale(scale.value)
            .clickable { 
                onClick()
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(gradientColors)
                )
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun VibeCheckScreen(onBack: () -> Unit) {
    var vibeResult by remember { mutableStateOf<String?>(null) }
    var isChecking by remember { mutableStateOf(false) }
    
    // Move infiniteTransition outside conditional
    val infiniteTransition = rememberInfiniteTransition(label = "scan")
    val scanY by infiniteTransition.animateFloat(
        initialValue = -150f,
        targetValue = 150f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scan"
    )
    
    val vibes = listOf(
        "VALID ASF 🔥" to "you're literally the main character",
        "MID TIER 😐" to "could use more aura points tbh",
        "COOKED 💀" to "touch grass immediately",
        "SIGMA MALE 🐺" to "based and redpilled fr fr",
        "NPC ENERGY 🤖" to "doing the default dance irl"
    )
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
        Text(
            text = "🧠 VIBE CHECK",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = NeonPink
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        if (vibeResult == null && !isChecking) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(NeonPink)
                        .offset(y = scanY.dp)
                )
                
                Text(
                    text = "PLACE FINGER\nTO SCAN",
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(30.dp))
            
            Button(
                onClick = {
                    isChecking = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(200.dp, 60.dp)
            ) {
                Text("START SCAN", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        } else if (isChecking) {
            CircularProgressIndicator(
                color = NeonPink,
                modifier = Modifier.size(100.dp),
                strokeWidth = 8.dp
            )
            
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(2000)
                vibeResult = vibes.random().first
                isChecking = false
            }
        } else {
            val result = vibes.find { it.first == vibeResult }
            
            Text(
                text = vibeResult!!,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = CyberYellow,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            
            Text(
                text = result?.second ?: "",
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            Button(
                onClick = { 
                    vibeResult = null 
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("CHECK AGAIN", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Text(
            text = "← back",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier
                .clickable { onBack() }
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp)
        )
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun BrainrotGenerator(onBack: () -> Unit) {
    var brainrot by remember { mutableStateOf("tap to generate") }
    
    val brainrots = listOf(
        "skibidi toilet rizzing up the fanum tax gyatt",
        "baby gronk needs to lock in with livvy dunne",
        "sigma male grindset ohio final boss",
        "looksmaxxing mewing mogging looksmaxxer",
        "negative aura points + ratio + L + cope",
        "hawk tuah 24 brat summer edit",
        "lowkey highkey no cap fr fr on god",
        "bussin respectfully based cringe normie",
        "edge of the edge edge edge edge",
        "did you pray today? aura farming in ohio"
    )
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
        Text(
            text = "🔥 BRAINROT",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = ElectricBlue
        )
        
        Text(
            text = "gen alpha translator",
            fontSize = 14.sp,
            color = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .clickable { brainrot = brainrots.random() },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(alpha = 0.5f)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = brainrot,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (brainrot == "tap to generate") Color.Gray else CyberYellow,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Button(
            onClick = { brainrot = brainrots.random() },
            colors = ButtonDefaults.buttonColors(containerColor = WackyPurple),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("GENERATE 🎲", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Text(
            text = "← back",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier
                .clickable { onBack() }
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp)
        )
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun RizzCalculator(onBack: () -> Unit) {
    var rizzScore by remember { mutableStateOf(0) }
    var isCalculating by remember { mutableStateOf(false) }
    
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        
        Text(
            text = "🎯 RIZZ CALC",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = SlimeGreen
        )
        
        Text(
            text = "aura points calculator",
            fontSize = 14.sp,
            color = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(60.dp))
        
        if (isCalculating) {
            Box(
                modifier = Modifier.size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxSize(),
                    color = SlimeGreen,
                    strokeWidth = 12.dp
                )
            }
            
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(1500)
                rizzScore = Random.nextInt(0, 101)
                isCalculating = false
            }
        } else if (rizzScore == 0) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(SlimeGreen.copy(alpha = 0.5f), Color.Transparent)
                        )
                    )
                    .scale(pulse),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "?",
                    fontSize = 80.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SlimeGreen
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            Button(
                onClick = { isCalculating = true },
                colors = ButtonDefaults.buttonColors(containerColor = SlimeGreen),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(200.dp, 60.dp)
            ) {
                Text("CALCULATE RIZZ", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        } else {
            val (message, color) = when (rizzScore) {
                in 80..100 -> "GOD TIER RIZZ" to CyberYellow
                in 60..79 -> "VALID RIZZ" to SlimeGreen
                in 40..59 -> "NEEDS WORK" to HotOrange
                else -> "TOUCH GRASS" to NeonPink
            }
            
            Text(
                text = "$rizzScore",
                fontSize = 100.sp,
                fontWeight = FontWeight.Black,
                color = color
            )
            
            Text(
                text = message,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            Button(
                onClick = { 
                    rizzScore = 0
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("TRY AGAIN", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Text(
            text = "← back",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier
                .clickable { onBack() }
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 64.dp)
        )
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}
