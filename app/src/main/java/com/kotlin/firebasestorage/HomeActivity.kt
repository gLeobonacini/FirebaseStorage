package com.kotlin.firebasestorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var cr: CollectionReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        config()

        btSalvar.setOnClickListener {
            val prod = getData()
            sendProd(prod)
        }

        btUpdate.setOnClickListener {
            val prod = getData()
            updateProd(prod)
        }

        btDelete.setOnClickListener {
            deleteProd()
        }

        readProd()
    }

    private fun config(){
        db = FirebaseFirestore.getInstance()
        cr = db.collection("produtos")
    }

    private fun getData(): MutableMap<String, Any>{
        val prod: MutableMap<String,Any> = HashMap()

        prod["nome"] = txtNome.text.toString()
        prod["quantidade"] = txtQuantidade.text.toString().toInt()
        prod["preco"] = txtPreco.text.toString().toDouble()

        return prod
    }

    private fun sendProd(prod: MutableMap<String, Any>){
        val nome = txtNome.text.toString()

        cr.document(nome).set(prod).addOnSuccessListener {

        }.addOnFailureListener {
            Log.i("TAG",it.toString())
        }
    }

    private fun updateProd(prod: MutableMap<String, Any>){
        cr.document("Caneta Azul").update(prod).addOnSuccessListener {
            Log.i("TAG","Produto atualizado")
        }.addOnFailureListener {
            Log.i("TAG",it.toString())
        }
    }

    private fun deleteProd(){
       cr.document("Guitarra").delete().addOnSuccessListener {
           Log.i("TAG","Produto deletado")
       }.addOnFailureListener {
           Log.i("TAG",it.toString())
       }
    }

    private fun readProd(){
        db.collection("users").get()
            .addOnCompleteListener {task->
                if(task.isSuccessful){
                    for (document in task.result!!){
                        Log.d("TAG",document.id + " => " + document.data)
                    }
                }else{
                    Log.w("TAG", "Error getting documents.", task.exception)
                }
            }
    }
}