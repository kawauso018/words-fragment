package com.example.wordsfragmentapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordsfragmentapp.databinding.FragmentWordListBinding

//定数を入れるとファイルにアイコンになる
const val TAG = "MainActivity"

class WordListFragment : Fragment() {

    //コンパニオンオブジェクトを作成
    companion object{
        //constをつけるとgetXXX()メソッドを一つ挟まずに直接変数にアクセスする。（今回はあまり関係ないかも）
        //コンパイル時定数とよばれる
        //valと似ており変更されない値
        const val LETTER = "letter"
        const val SEARCH_PREFIX = "https://www.google.com/search?q="
    }

    private var _binding: FragmentWordListBinding? = null
    private val binding get() = _binding!!
    private lateinit var letterId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //（多分）arguments?.letはnav_graphで定義したletter:Stringを呼び出している
        //arguments が null の場合、ラムダは実行されません。
        arguments?.let{
            letterId=it.getString(LETTER).toString()
        }
        Log.d(TAG, "onStart onCreate(WordListFragment)")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //ビューをインフレートし、_bindingの値を設定
        _binding = FragmentWordListBinding.inflate(inflater,container,false)
        //ルートビューを作成
        val view = binding.root
        Log.d(TAG, "onStart onCreateView(WordListFragment)")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //リサイクラービューのプロパティにFragmentLetterListBindingのリサイクラービュープロパティをバインド
        //よってfindViewById()を呼び出す必要がない
        //nullを許容する → this.context
        //nullを許容しない → requireContext()
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = WordAdapter(letterId, requireContext())
        recyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        Log.d(TAG, "onStart onViewCreated(WordListFragment)")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_bindingプロパティをnullにする
        _binding = null
        Log.d(TAG, "onStart onDestroyView(WordListFragment)")
    }
}