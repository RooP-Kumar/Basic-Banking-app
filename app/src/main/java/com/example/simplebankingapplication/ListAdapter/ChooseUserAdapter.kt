package com.example.simplebankingapplication.ListAdapter

import android.app.Dialog
import android.content.Context
import android.media.Image
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.simplebankingapplication.DB.BankViewModel
import com.example.simplebankingapplication.DB.Transections
import com.example.simplebankingapplication.DB.Users
import com.example.simplebankingapplication.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text

class ChooseUserAdapter(
        private val context: Context,
        private val currentUser: Users,
        private val navController: NavController,
        private val bankViewModel: BankViewModel,
        private val viewForSnackbar: View

        ) : RecyclerView.Adapter<ChooseUserAdapter.MyHolder>() {
    private var lastPosition = -1
    private val users = ArrayList<Users>()
    private lateinit var timer: CountDownTimer
    private var progress = 0


    class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.chooseUserName)
        val email = itemView.findViewById<TextView>(R.id.chooseUserEmail)
        val account = itemView.findViewById<TextView>(R.id.chooseUserAccount)
        val image = itemView.findViewById<ImageView>(R.id.circularImageView)
        val selectUser = itemView.findViewById<ConstraintLayout>(R.id.chooseUser)
        val userChooseCardView = itemView.findViewById<CardView>(R.id.userChooseCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.choose_user_layout, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = users[position].userName
        holder.email.text = users[position].userEmail
        holder.account.text = users[position].bankAccount

        Glide.with(context).load(users[position].userImage).into(holder.image)

        // Adding animation
        if (position > lastPosition) {
         holder.userChooseCardView.translationX = 700f
         holder.userChooseCardView.animate().translationX(0f).setDuration(500).setStartDelay((100 + (position * 50)).toLong()).start()
         lastPosition = position
        }

        holder.selectUser.setOnClickListener {
            chooseAmountToPay(position)
        }

    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun updateList(newUsers: List<Users>, currentUser: Int) {
        users.clear()
        users.addAll(newUsers)
        users.removeAt(currentUser) // It will remove the selected user because of that user can not transfer money to himself.
        notifyDataSetChanged()
    }

    private fun chooseAmountToPay(position: Int) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.choose_amount_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        Glide.with(context).load(users[position].userImage).into(dialog.findViewById<ImageView>(R.id.dialogUserImage))
        dialog.findViewById<TextView>(R.id.dialogUserName).text = users[position].userName


        dialog.findViewById<RelativeLayout>(R.id.cancleBtn).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<RelativeLayout>(R.id.sendBtn).setOnClickListener {
            val amountEditTextLayout = dialog.findViewById<TextInputLayout>(R.id.amountEditTextLayout)
            val amount = dialog.findViewById<EditText>(R.id.dialogAmount).text.toString()
            if (amount.isEmpty() || amount.toInt() <= 0){
                amountEditTextLayout.error = "Plese! Enter amount."
            } else if (amount.toInt() > currentUser.userAmount) {
                amountEditTextLayout.error = "Not enough balance to sent"
            } else {
                showProcessingDialog(position, amount.toInt())
                dialog.dismiss()
            }

        }

        dialog.show()
    }

    private fun showProcessingDialog(position: Int, amount: Int) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.payment_processing_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val progressbar = dialog.findViewById<ProgressBar>(R.id.progressBar)
        val processingText = dialog.findViewById<TextView>(R.id.processingText)
        val cancelBtn = dialog.findViewById<RelativeLayout>(R.id.paymentCancelBtn)

        timer = object : CountDownTimer(5000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                progress += 1

                // Logic to show processing text
                if (progress%3 == 1){
                    processingText.text = context.resources.getString(R.string.firstprocessingtext)
                } else if (progress%3 == 2) {
                    processingText.text = context.resources.getString(R.string.secondprocessingtext)
                } else {
                    processingText.text = context.resources.getString(R.string.thirdprocessingtext)
                }

            }

            override fun onFinish() {
                progressbar.visibility = View.GONE
                var destinationUserAmount = users[position].userAmount
                destinationUserAmount += amount
                val destinationUserId = users[position].userId
                bankViewModel.updateAmount(destinationUserAmount, destinationUserId) // adding money to choose user.

                var tempUseramount = currentUser.userAmount
                tempUseramount -= amount
                bankViewModel.updateAmount(tempUseramount, currentUser.userId) // decrease money from choose user.
                Snackbar.make(context, viewForSnackbar, "Money Successfully sent.", Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()

                addMyTransection(position, amount, true)

                navController.navigate(R.id.action_chooseToPayFragment_to_usersFragment)
            }
        }.start()

        cancelBtn.setOnClickListener {
            timer.cancel()
            if (progress != 5) {
                Snackbar.make(context, viewForSnackbar, "Transection Failed.", Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
                addMyTransection(position, amount, false)
                navController.navigate(R.id.action_chooseToPayFragment_to_usersFragment)
            }

        }

        dialog.show()

    }

    private fun addMyTransection(position: Int, amount: Int, state: Boolean) {
        val transection = Transections(currentUser.userName, users[position].userName, amount, state)
        bankViewModel.insertTransection(transection)
    }
}