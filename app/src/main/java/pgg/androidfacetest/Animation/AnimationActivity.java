package pgg.androidfacetest.Animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pgg.androidfacetest.R;

/**
 * Created by PDD on 2018/3/17.
 */

public class AnimationActivity extends AppCompatActivity{

    @Bind(R.id.btn_property)
    Button btn_property;
    @Bind(R.id.btn_tween)
    Button btn_tween;
    @Bind(R.id.btn_frame)
    Button btn_frame;
    @Bind(R.id.iv_target)
    ImageView iv_target;
    @Bind(R.id.root)
    LinearLayout root;

    private static final String TAG = "AnimationActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_item);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        layoutAnimationController.setDelay(0.5f);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        root.setLayoutAnimation(layoutAnimationController);
    }

    @OnClick({R.id.btn_property,R.id.btn_tween,R.id.btn_frame})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_property:
                Animator animator = AnimatorInflater.loadAnimator(this, R.animator.animator);
                animator.setTarget(iv_target);
                animator.start();

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(
                        ObjectAnimator.ofFloat(iv_target,"rotationX",0,360),
                        ObjectAnimator.ofFloat(iv_target, "rotationY", 0, 360),
                        ObjectAnimator.ofFloat(iv_target, "rotation", 0, 360),
                        ObjectAnimator.ofFloat(iv_target, "translationX", 0, 90),
                        ObjectAnimator.ofFloat(iv_target, "alpha", 1, 0.25f, 1)
                );
                animatorSet.setDuration(5000).start();
                break;
            case R.id.btn_tween:
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
                iv_target.startAnimation(animation);
                break;
            case R.id.btn_frame:
                iv_target.setBackgroundDrawable(getResources().getDrawable(R.drawable.frame_anim));
                ((AnimationDrawable) iv_target.getBackground()).start();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
    }
}
