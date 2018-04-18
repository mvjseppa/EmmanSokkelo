package seppala.mikko.EmmanSokkelo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class HeroFragment : Fragment() {

    private lateinit var textView : TextView
    private lateinit var imageView : ImageView

    var imageId : Int = R.drawable.hero_500px
    var name : String = "Name placeholder"

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        arguments = savedInstanceState
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_hero, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?)
    {
        textView = view?.findViewById(R.id.hero_name_text)!!
        imageView = view.findViewById(R.id.hero_fragment_image)

        arguments = savedInstanceState

        textView.text = name
        imageView.setImageDrawable(ContextCompat.getDrawable(context, imageId))

        super.onViewCreated(view, savedInstanceState)
    }

    override fun setArguments(args: Bundle?)
    {

        imageId = args?.getInt("imageId") ?: imageId
        name = args?.getString("name") ?: name

        super.setArguments(args)
    }

    override fun onSaveInstanceState(outState: Bundle?)
    {
        outState?.putString("name", name)
        outState?.putInt("imageId", imageId)
        super.onSaveInstanceState(outState)
    }
}
