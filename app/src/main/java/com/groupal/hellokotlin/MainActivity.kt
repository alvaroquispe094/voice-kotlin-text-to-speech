package com.groupal.hellokotlin

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOLUME
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)



        findViewById<Button>(R.id.btnPlay).setOnClickListener { speak() }
    }

    private fun speak(){
        var message: String = findViewById<TextView>(R.id.message).text.toString()

        val volumen = Bundle()
        volumen.putFloat(KEY_PARAM_VOLUME, 0.50f)

        if(message.isEmpty()){
            findViewById<TextView>(R.id.tvStatus).text = getString(R.string.tts_warning)
            message = "¿Aun no ha ingresado un texto? Ingrese uno por favor!"
        }
        tts!!.speak(message, TextToSpeech.QUEUE_FLUSH, volumen, "")
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            findViewById<TextView>(R.id.tvStatus).text = getString(R.string.tts_active)
            tts!!.language = Locale("ES")
        }else{
            findViewById<TextView>(R.id.tvStatus).text = getString(R.string.tts_no_active)
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        super.onDestroy()
    }
}