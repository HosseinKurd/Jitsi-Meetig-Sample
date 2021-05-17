package modularization.libraries.uicomponents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import modularization.libraries.utils.PublicFunction;
import modularization.libraries.utils.helpers.LogHelper;

public class MagicalImageView extends AppCompatImageView {

    private static final String TAG = "MagicalImageView";

    public MagicalImageView(Context context) {
        super(context);
    }

    public MagicalImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagicalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            @SuppressLint("Recycle") TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MagicalImageView, 0, 0);
            imageSquare = a.getBoolean(R.styleable.MagicalImageView_square_image, false);
            imageRound = a.getBoolean(R.styleable.MagicalImageView_round_image, false);
            imageAspectRatio = a.getBoolean(R.styleable.MagicalImageView_aspect_ratio, false);
        } catch (Exception ex) {
            Log.i(TAG, "MagicalImageView: ");
        }
    }

    boolean imageSquare = false;

    boolean imageRound = false;

    boolean imageAspectRatio = false;

    private LogHelper logHelper = new LogHelper(MagicalImageView.class);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        try {
            if (imageSquare) {
                setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
            } else if (imageAspectRatio) {
                // 16 * 9
                int width = getMeasuredWidth();

                //force a 16:9 aspect ratio
                int height = Math.round(width * .5625f);
                setMeasuredDimension(width, height);
            }
        } catch (Exception e) {
            logHelper.e("onMeasure", e);
        }
    }

    public void setImageUrl(File file) {
        try {
            Glide
                    .with(getContext())
                    .load(file)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrl(Uri url) {
        setImageUrl(url.toString());
    }

    public void setImageUrl(String url) {
        try {
            Glide
                    .with(getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .apply(new RequestOptions().placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder))
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrl(Bitmap bitmap) {
        try {
            Glide
                    .with(getContext())
                    .load(bitmap)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrl(RoundedBitmapDrawable bitmap) {
        try {
            Glide
                    .with(getContext())
                    .load(bitmap)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlRound(String url) {
        try {
            Glide
                    .with(getContext())
                    .load(url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlRound(Uri uri) {
        try {
            Glide
                    .with(getContext())
                    .load(uri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlRound(String url, int placeHolder) {
        try {
            Glide
                    .with(getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrl(String url, int placeHolder) {
        try {
            Glide
                    .with(getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrl(String url, boolean circle) {
        try {
            if (circle) {
                Glide
                        .with(getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(false)
                        .centerCrop()
                        .optionalCircleCrop()
                        .into(this);
            } else {
                Glide
                        .with(getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(false)
                        .centerCrop()
                        .into(this);
            }
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrl(int resource) {
        try {
            Glide
                    .with(getContext())
                    .load(resource)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)
                    .into(this);
        } catch (Exception e) {
            logHelper.e("ImageViewSetUrl set Image resource : " + e);
        }
    }

    public void setImageResRound(int resource) {
        try {
            Glide
                    .with(getContext())
                    .load(resource)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            logHelper.e("ImageViewSetUrl set Image resource : " + e);
        }
    }

    public void setImageUrlCurve(String url, int radius) {
        try {
            Glide
                    .with(getContext())
                    .load(url)
                    .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(((int) PublicFunction.convertDpToPixels(radius, getContext())))))
                    .placeholder(R.drawable.ic_placeholder)
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageUrlCurve(int res) {
        try {
            Glide
                    .with(getContext())
                    .load(res)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageBitmapRound(Bitmap bitmap, int placeHolder) {
        try {
            Glide
                    .with(getContext())
                    .load(bitmap)
                    .placeholder(placeHolder)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageUriRound(Uri uri, int placeHolder) {
        try {
            Glide
                    .with(getContext())
                    .load(uri)
                    .placeholder(placeHolder)
                    .apply(RequestOptions.circleCropTransform().error(R.drawable.ic_placeholder))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageUriCurved(Uri uri, int placeHolder) {
        try {
            Glide
                    .with(getContext())
                    .load(uri)
                    .placeholder(placeHolder)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageUriWithPlaceHolder(Uri uri, int placeHolder) {
        try {
            Glide
                    .with(getContext())
                    .load(uri)
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlWithPlaceHolder(String url, int placeHolder) {
        try {
            Glide
                    .with(getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlRoundWithPlaceHolder(String url, int placeHolder) {
        try {
            Bitmap placeholder = BitmapFactory.decodeResource(getResources(), placeHolder);
            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), placeholder);
            circularBitmapDrawable.setCircular(true);
            Glide
                    .with(getContext())
                    .load(url)
                    .placeholder(circularBitmapDrawable)
                    .error(circularBitmapDrawable)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlCurveWithRadius(int res, int radius, int placeHolder) {
        try {
            Glide
                    .with(getContext())
                    .load(res)
                    .centerCrop()
                    .placeholder(placeHolder)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

    public void setImageUrlCurveWithRadius(String url, int radius, int placeHolder) {
        try {
            Glide
                    .with(getContext())
                    .load(url)
                    .centerCrop()
                    .placeholder(placeHolder)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                    .into(this);
        } catch (Exception e) {
            logHelper.e(e);
        }
    }

}
