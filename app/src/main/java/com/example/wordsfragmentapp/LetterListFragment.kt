package com.example.wordsfragmentapp

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsfragmentapp.databinding.FragmentLetterListBinding

class LetterListFragment : Fragment() {

    /*
    ビューバインディングを実装するために、バインディングクラスを初期化する
    ビューバインディング：findViewByIdを使わずにTextViewやButtonなどにアクセスできるようになり、無効なIDや型を指定するリスクを防ぐことができる機能
    FragmentLetterListBinding：GradleにViewBindingを有効にするとレイアウトファイルに生成されるバインディングクラス
    今回だとfragment_letter_list.xmlのバインディングクラスが対象
     */
    //プロパティが直接アクセスされることを意図していない場合はアンダースコアをつける
    private var _binding: FragmentLetterListBinding? = null
    //get()：_bindingのプロパティが「get-only（読み取り専用）」であることを示す
    private val binding get() = _binding!!
    //リサイクラービューのプロパティを作成
    private lateinit var recyclerView: RecyclerView
    //アプリのレイアウト状態を追跡するために線形レイアウトマネージャーがデフォルトで使用されるようにする
    private var isLinearLayoutManager = true

    //フラグメント版のライフサイクル
    //onCreate：フラグメントをインスタンス化する
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //アプリバーのフラグメントがオプションメニューの設定に参加していることをシステムに宣言する（？）
        //onCreateOptionsMenuおよび関連するメソッドの呼び出しを受信する
        setHasOptionsMenu(true)
        Log.d(TAG, "onStart Create(LetterListFragment)")
    }

    //onCreateView：レイアウトをインフレートする
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //ビューをインフレートし、_bindingの値を設定
        _binding = FragmentLetterListBinding.inflate(inflater,container,false)
        //ルートビューを作成
        val view = binding.root
        Log.d(TAG, "onStart onCreateView(LetterListFragment)")
        return view
    }

    //onCreateView：findViewByIdを呼び出して特定のビューをプロパティにバインドする
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //リサイクラービューのプロパティにFragmentLetterListBindingのリサイクラービュープロパティをバインド
        //よってfindViewById()を呼び出す必要がない
        recyclerView = binding.recyclerView
        chooseLayout()
        Log.d(TAG, "onStart onViewCreated(LetterListFragment)")
    }

    //onDestroyView：フラグメントをデストロイ状態にする
    override fun onDestroyView() {
        super.onDestroyView()
        //_bindingプロパティをnullにする
        _binding = null
        Log.d(TAG, "onStart onDestroyView(LetterListFragment)")
    }

    //onCreateOptionsMenu：メニューをインフレートする
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //メニューをインフレートして、追加の設定を行う
        inflater.inflate(R.menu.layout_menu, menu)
        //layout_menu.xmlのメニューアイテムを取得
        val layoutButton = menu.findItem(R.id.action_switch_layout)
        //メニューアイテムを渡しアイコンをセットする
        setIcon(layoutButton)
        Log.d(TAG, "onStart onCreateOptionsMenu(LetterListFragment)")
    }

    //レイアウト選択する関数
    private fun chooseLayout(){
        //thisをレイアウトマネージャーのコンテキストして渡せないことに注意！
        if(isLinearLayoutManager){
            recyclerView.layoutManager = LinearLayoutManager(context)
        }else{
            recyclerView.layoutManager = GridLayoutManager(context, 4)
        }
        //アダプターの割当て
        recyclerView.adapter = LetterAdapter()
    }

    //メニューを切り替えたときにアイコンも切り替える
    private fun setIcon(menuItem: MenuItem?){
        //thisをレイアウトマネージャーのコンテキストして渡せないことに注意！
        //requireContext(): 非nullのContextを値として返す

        if(menuItem == null)
            return
        //ContextCompatはコンテキストの機能にアクセスする
        //たぶんメニューのアイコン情報にアクセスしている？
        menuItem.icon =
            if(isLinearLayoutManager) ContextCompat.getDrawable(this.requireContext(),R.drawable.ic_grid_layout)
            else ContextCompat.getDrawable(this.requireContext(),R.drawable.ic_linear_layout)
    }

    //メニューボタンが選択されたときに、実際にchooseLayout()を呼び出す
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //layout_menu.xmlのアイテムidによって条件分岐（多分ずっと一致するはず）
        return when(item.itemId) {
            R.id.action_switch_layout -> {
                //ボタンが押されたらレイアウト状態を切り替える
                isLinearLayoutManager = !isLinearLayoutManager
                //レイアウトを選択
                chooseLayout()
                //メニューアイテムを渡しアイコンをセットする
                setIcon(item)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}