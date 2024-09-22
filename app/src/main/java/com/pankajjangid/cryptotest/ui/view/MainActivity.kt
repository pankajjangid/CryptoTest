package com.pankajjangid.cryptotest.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.pankajjangid.cryptotest.utils.Constant
import com.pankajjangid.cryptotest.R
import com.pankajjangid.cryptotest.utils.Utils
import com.pankajjangid.cryptotest.local.entity.CurrencyEntity
import com.pankajjangid.cryptotest.ui.theme.CryptoTestTheme
import com.pankajjangid.cryptotest.viewmodel.CurrencyViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTestTheme {
                Scaffold(

                ) { paddingValue ->
                    Surface(
                        modifier = Modifier
                            .padding(paddingValue)
                            .fillMaxSize(1f)
                            .background(color = Color.White)
                    ) {
                        val viewModel: CurrencyViewModel = koinViewModel()
                        val searchText by viewModel.searchText.collectAsState()
                        val currencyList by viewModel.currency.collectAsState()
                        val progress by viewModel.progress.collectAsState()
                        val message by viewModel.message.collectAsState()
                        message?.let {
                           val context =  LocalContext.current
                            Toast.makeText(context, context.getString(it), Toast.LENGTH_SHORT).show()
                            viewModel.clearErrorMessage()
                        }
                        viewModel.currencySetA = Utils.parseJsonToModel(
                            Utils.getFileFromJson(
                                this,
                                Constant.CURRENCY_SET_A
                            )
                        )
                        viewModel.currencySetB = Utils.parseJsonToModel(
                            Utils.getFileFromJson(
                                this,
                                Constant.CURRENCY_SET_B
                            )
                        )


                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {

                            TextField(
                                value = searchText,
                                onValueChange = viewModel::onSearchTextChange,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                placeholder = { Text(text = "Search") },
                                trailingIcon = {
                                    if (searchText.isNotEmpty()) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(start = 8.dp, end = 8.dp)
                                                .clickable {
                                                    viewModel.clearSearch()
                                                }
                                        )
                                    } else {
                                        null
                                    }
                                },

                                )
                            Spacer(modifier = Modifier.height(6.dp))
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Button(
                                        onClick = {
                                            viewModel.clearDB()
                                        },
                                    ) {
                                        Text("Clear")
                                    }
                                    Button(
                                        onClick = {
                                            viewModel.insertToDatabase()
                                        },
                                    ) {
                                        Text("Insert")
                                    }
                                    Button(
                                        onClick = {
                                            viewModel.loadCurrencyListByType(Constant.CURRENCY_TYPE_CRYPTO)

                                        },
                                    ) {
                                        Text("Crypto")
                                    }
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Button(
                                        onClick = {
                                            viewModel.loadCurrencyListByType(Constant.CURRENCY_TYPE_FIAT)

                                        },
                                    ) {
                                        Text("Fiat")
                                    }
                                    Button(
                                        onClick = {
                                            viewModel.loadAllCurrencyFromDatabase()

                                        },
                                    ) {
                                        Text("Crypto & Fiat")
                                    }

                                }


                            }
                            Spacer(modifier = Modifier.height(6.dp))

                            if (progress) {
                                LoaderView()

                            } else {
                                if (currencyList.isNotEmpty()) {
                                    CurrencyList(currencyList)
                                } else {
                                    EmptyView()
                                }
                            }
                        }
                    }

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


@Composable
fun LoaderView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "No Data Found",
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
        )

        ContextCompat.getDrawable(LocalContext.current, R.drawable.no_data)?.let {
            Image(
                bitmap = it.toBitmap().asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .padding(16.dp)
            )
        }
    }

}

@Composable
fun CurrencyList(currencyList: List<CurrencyEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        items(currencyList) { currency ->

            Row(
                modifier = Modifier
                    .fillParentMaxWidth(1f)
                    .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .drawBehind {
                            drawCircle(
                                color = Color.Black,
                                radius = this.size.maxDimension
                            )
                        },
                    text = currency.name.first().toString(),
                    style = TextStyle(color = Color.White, fontSize = 20.sp)
                )
                Text(
                    text = currency.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 8.dp)
                )
                Text(text = currency.symbol, modifier = Modifier.padding(start = 8.dp, end = 8.dp))
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoTestTheme {
        LoaderView()
    }
}

