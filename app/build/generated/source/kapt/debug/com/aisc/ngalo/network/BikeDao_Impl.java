package com.aisc.ngalo.network;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.aisc.ngalo.models.Bike;
import com.aisc.ngalo.models.Category;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BikeDao_Impl implements BikeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Bike> __insertionAdapterOfBike;

  private final EntityDeletionOrUpdateAdapter<Bike> __deletionAdapterOfBike;

  private final EntityDeletionOrUpdateAdapter<Bike> __updateAdapterOfBike;

  public BikeDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBike = new EntityInsertionAdapter<Bike>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `mylobikes` (`id`,`imageUrl`,`name`,`price`,`description`,`options`,`quantity`,`position`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Bike value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getImageUrl());
        }
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getPrice() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPrice());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescription());
        }
        if (value.getOptions() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, __Category_enumToString(value.getOptions()));
        }
        if (value.getQuantity() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getQuantity());
        }
        if (value.getPosition() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, value.getPosition());
        }
      }
    };
    this.__deletionAdapterOfBike = new EntityDeletionOrUpdateAdapter<Bike>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `mylobikes` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Bike value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfBike = new EntityDeletionOrUpdateAdapter<Bike>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `mylobikes` SET `id` = ?,`imageUrl` = ?,`name` = ?,`price` = ?,`description` = ?,`options` = ?,`quantity` = ?,`position` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Bike value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getImageUrl());
        }
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getPrice() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPrice());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescription());
        }
        if (value.getOptions() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, __Category_enumToString(value.getOptions()));
        }
        if (value.getQuantity() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, value.getQuantity());
        }
        if (value.getPosition() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindLong(8, value.getPosition());
        }
        if (value.getId() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getId());
        }
      }
    };
  }

  @Override
  public void insert(final Bike bike) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfBike.insert(bike);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Bike bike) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfBike.handle(bike);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Bike bike) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfBike.handle(bike);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Bike>> getAll() {
    final String _sql = "SELECT * FROM mylobikes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"mylobikes"}, false, new Callable<List<Bike>>() {
      @Override
      public List<Bike> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "options");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final List<Bike> _result = new ArrayList<Bike>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Bike _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final Category _tmpOptions;
            _tmpOptions = __Category_stringToEnum(_cursor.getString(_cursorIndexOfOptions));
            final Integer _tmpQuantity;
            if (_cursor.isNull(_cursorIndexOfQuantity)) {
              _tmpQuantity = null;
            } else {
              _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            }
            final Integer _tmpPosition;
            if (_cursor.isNull(_cursorIndexOfPosition)) {
              _tmpPosition = null;
            } else {
              _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            }
            _item = new Bike(_tmpId,_tmpImageUrl,_tmpName,_tmpPrice,_tmpDescription,_tmpOptions,_tmpQuantity,_tmpPosition);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Bike> get(final long id) {
    final String _sql = "SELECT * FROM mylobikes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[]{"mylobikes"}, false, new Callable<Bike>() {
      @Override
      public Bike call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "price");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfOptions = CursorUtil.getColumnIndexOrThrow(_cursor, "options");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfPosition = CursorUtil.getColumnIndexOrThrow(_cursor, "position");
          final Bike _result;
          if(_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpPrice;
            if (_cursor.isNull(_cursorIndexOfPrice)) {
              _tmpPrice = null;
            } else {
              _tmpPrice = _cursor.getString(_cursorIndexOfPrice);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final Category _tmpOptions;
            _tmpOptions = __Category_stringToEnum(_cursor.getString(_cursorIndexOfOptions));
            final Integer _tmpQuantity;
            if (_cursor.isNull(_cursorIndexOfQuantity)) {
              _tmpQuantity = null;
            } else {
              _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            }
            final Integer _tmpPosition;
            if (_cursor.isNull(_cursorIndexOfPosition)) {
              _tmpPosition = null;
            } else {
              _tmpPosition = _cursor.getInt(_cursorIndexOfPosition);
            }
            _result = new Bike(_tmpId,_tmpImageUrl,_tmpName,_tmpPrice,_tmpDescription,_tmpOptions,_tmpQuantity,_tmpPosition);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __Category_enumToString(final Category _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case BUY: return "BUY";
      case HIRE: return "HIRE";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private Category __Category_stringToEnum(final String _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case "BUY": return Category.BUY;
      case "HIRE": return Category.HIRE;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
