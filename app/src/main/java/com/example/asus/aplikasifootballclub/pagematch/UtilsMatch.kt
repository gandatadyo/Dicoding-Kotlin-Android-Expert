package com.example.asus.aplikasifootballclub.pagematch

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.asus.aplikasifootballclub.R
import com.example.asus.aplikasifootballclub.model.gsonMatchDetail
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView
import java.text.SimpleDateFormat
import java.util.*

class UtilsMatch {

    class AdapterMain(private val teams: List<gsonMatchDetail>, val context: Context): RecyclerView.Adapter<AdapterMainHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMainHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_match, parent, false)
            return AdapterMainHolder(view)
        }
        override fun onBindViewHolder(holder: AdapterMainHolder, position: Int) {
            holder.bindItem(context,teams[position])
        }
        override fun getItemCount(): Int = teams.size
    }

    class AdapterMainHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val lbldate: TextView = itemView.find(R.id.lblDate_list)
        private val lbltime: TextView = itemView.find(R.id.lblTime_List)
        private val lblhome: TextView = itemView.find(R.id.lblhomeclub_list)
        private val lblaway: TextView = itemView.find(R.id.lblaway_list)
        private val btnAlrm: ImageButton = itemView.find(R.id.imageButton)

        fun toGMTFormat(date: String, time: String): Date? {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val dateTime = "$date $time"
            return formatter.parse(dateTime)
        }
        fun Date.getStringTimeStampWithTime(): String {
            val dateFormat = SimpleDateFormat("HH:mm:ss",Locale.getDefault())
            return dateFormat.format(this)
        }
        fun Date.getStringTimeStampWithDate(): String {
            val dateFormat = SimpleDateFormat(" dd MM yyyy",Locale.getDefault())
            return dateFormat.format(this)
        }
        fun bindItem(context: Context, teams: gsonMatchDetail) {
            var datet = toGMTFormat(teams.dateEvent.toString(),teams.strTime!!.replace("+00:00",""))
            lbldate.text = datet?.getStringTimeStampWithDate()
            lbltime.text = datet?.getStringTimeStampWithTime()
            lblhome.text = teams.strHomeTeam
            lblaway.text = teams.strAwayTeam
            itemView.setOnClickListener{
                context.startActivity<MatchDetailActivity>("idEvent" to teams.idEvent.toString())
            }
            btnAlrm.setOnClickListener(){
                val beginTime = Calendar.getInstance()
                if(datet!=null) {
                    beginTime.set(datet?.year, datet?.month, datet?.day, 7, 30)
                    val intent = Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, teams.strHomeTeam+" vs "+teams.strAwayTeam)
                            .putExtra(CalendarContract.Events.DESCRIPTION, teams.strHomeTeam+" vs "+teams.strAwayTeam)
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                    context.startActivity(intent)
                }
            }
        }
    }

    class ShceduleLeagueDetailUI : AnkoComponent<MatchDetailActivity> {
        companion object {
            const val dateEvent =1
            const val strHomeTeam =2
            const val strAwayTeam =3
            const val strHomeGoalDetails =4
            const val strAwayGoalDetails =5
            const val intHomeScore =6
            const val intAwayScore =7
            const val strHomeLineupGoalkeeper =8
            const val strAwayLineupGoalkeeper =9
            const val strHomeLineupDefense =10
            const val strAwayLineupDefense =11
            const val strHomeLineupMidfield =12
            const val strAwayLineupMidfield =13
            const val strHomeLineupForward =14
            const val strAwayLineupForward =15
            const val strHomeLineupSubstitutes =16
            const val strAwayLineupSubstitutes =17
            const val imagestrHomeTeam =18
            const val imagestrAwayTeam =19
        }

        override fun createView(ui: AnkoContext<MatchDetailActivity>) = with(ui) {
            nestedScrollView {
                id = R.id.nestedloopdetail
                lparams(matchParent, matchParent)
                verticalLayout {
                    textView {
                        id= dateEvent
                        text = "dateEvent"
                        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        textSize = 20f
                    }.lparams(width = matchParent, height = wrapContent){
                        topMargin = dip(15)
                    }
                    textView {
                        text = "VS"
                        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        textSize = 20f
                    }.lparams(width = matchParent, height = wrapContent){
                        topMargin = dip(5)
                        bottomMargin = dip(5)
                    }
                    relativeLayout() {
                        imageView{
                            id = imagestrHomeTeam
                            setImageResource(R.drawable.loading)
                            scaleType = ImageView.ScaleType.FIT_CENTER
                        }.lparams(width=dip(150),height=dip(80)){
                            topMargin = dip(10)
                            leftMargin = dip(10)
                            alignParentLeft()
                        }
                        imageView{
                            id = imagestrAwayTeam
                            setImageResource(R.drawable.loading)
                            scaleType = ImageView.ScaleType.FIT_CENTER
                        }.lparams(width=dip(150),height=dip(80)){
                            topMargin = dip(10)
                            rightMargin = dip(10)
                            alignParentRight()
                        }
                    }.lparams(width = matchParent, height = wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                    }
                    linearLayout {
                        textView {
                            id= strHomeTeam
                            text = "strHomeTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 20f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                            rightMargin = dip(15)
                        }
                        textView {
                            id= strAwayTeam
                            text = "strAwayTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 20f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                            leftMargin = dip(15)
                        }

                    }.lparams(width = matchParent, height = wrapContent) {
                        orientation = LinearLayout.VERTICAL
                    }
                    linearLayout{
                        orientation = LinearLayout.VERTICAL
                        setBackgroundColor(resources.getColor(android.R.color.darker_gray))
                    }.lparams(matchParent, dip(2)){
                        topMargin = dip(10)
                        bottomMargin = dip(10)
                        weight = 1f
                    }
                    linearLayout {
                        textView {
                            id= strHomeGoalDetails
                            text = "strHomeTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            text = "Goals"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 20f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            id= strAwayGoalDetails
                            text = "strAwayTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }

                    }.lparams(width = matchParent, height = wrapContent) {
                        orientation = LinearLayout.VERTICAL
                        bottomMargin = dip(10)
                    }
                    linearLayout {
                        textView {
                            id= intHomeScore
                            text = "strHomeTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 20f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            text = "Shots"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 20f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            id= intAwayScore
                            text = "strAwayTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 20f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }

                    }.lparams(width = matchParent, height = wrapContent) {
                        orientation = LinearLayout.VERTICAL
                        bottomMargin = dip(10)
                    }
                    linearLayout{
                        orientation = LinearLayout.VERTICAL
                        setBackgroundColor(resources.getColor(android.R.color.darker_gray))
                    }.lparams(matchParent, dip(2)){
                        topMargin = dip(10)
                        bottomMargin = dip(10)
                        weight = 1f
                    }
                    textView {
                        text = "Lineups"
                        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        textSize = 20f
                    }.lparams(width = matchParent, height = wrapContent){
                        topMargin = dip(15)
                        bottomMargin = dip(10)
                    }

                    linearLayout {
                        textView {
                            id= strHomeLineupGoalkeeper
                            text = "strHomeTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            text = "Goal Keeper"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 20f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            id= strAwayLineupGoalkeeper
                            text = "strAwayTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }

                    }.lparams(width = matchParent, height = wrapContent) {
                        orientation = LinearLayout.VERTICAL
                        bottomMargin = dip(15)
                    }

                    linearLayout {
                        textView {
                            id = strHomeLineupDefense
                            text = "strHomeTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            text = "Defence"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 20f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            id = strAwayLineupDefense
                            text = "strAwayTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }

                    }.lparams(width = matchParent, height = wrapContent) {
                        orientation = LinearLayout.VERTICAL
                        bottomMargin = dip(15)
                    }

                    linearLayout {
                        textView {
                            id = strHomeLineupMidfield
                            text = "strHomeTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            text = "Midfield"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 20f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            id = strAwayLineupMidfield
                            text = "strAwayTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }

                    }.lparams(width = matchParent, height = wrapContent) {
                        orientation = LinearLayout.VERTICAL
                        bottomMargin = dip(15)
                    }

                    linearLayout {
                        textView {
                            id= strHomeLineupForward
                            text = "strHomeTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            text = "Forward"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 20f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            id= strAwayLineupForward
                            text = "strAwayTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }

                    }.lparams(width = matchParent, height = wrapContent) {
                        orientation = LinearLayout.VERTICAL
                        bottomMargin = dip(15)
                    }

                    linearLayout {
                        textView {
                            id= strHomeLineupSubstitutes
                            text = "strHomeTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            text = "Substitues"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 20f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                        textView {
                            id= strAwayLineupSubstitutes
                            text = "strAwayTeam"
                            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                            textSize = 18f
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }

                    }.lparams(width = matchParent, height = wrapContent) {
                        orientation = LinearLayout.VERTICAL
                        bottomMargin = dip(15)
                    }
                }.lparams(matchParent, matchParent)
            }
        }
    }
}