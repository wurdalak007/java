package com.company;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.max;

public class VeryLong {

    VeryLong (String value){
        this.number = new ArrayList<Integer>(0);
        Integer start = 0;
        if (value.charAt(0) == '-') {
            isNegative = true;
            start = 1;
        } else {
            isNegative = false;
        }

        for (int i = value.length() - 1; i >= start; --i){
            char zero = '0';
            number.add(value.charAt(i) - zero);
        }

    }

    VeryLong (int other) {
        this.number = new ArrayList<Integer>(0);
        VeryLong res = new VeryLong(Integer.toString(other));
        this.isNegative = res.isNegative;
        this.number = res.number;
    }

    VeryLong (boolean other) {
        this.number = new ArrayList<Integer>(0);
        VeryLong res = new VeryLong(other ? 1 : 0);
        this.isNegative = res.isNegative;
        this.number = res.number;
    }

    VeryLong (long other) {
        this.number = new ArrayList<Integer>(0);
        VeryLong res = new VeryLong(Long.toString(other));
        this.isNegative = res.isNegative;
        this.number = res.number;
    }


    VeryLong (VeryLong other) {
        this.number = new ArrayList<Integer>(0);
        this.isNegative = other.isNegative;
        this.number.addAll(other.number);
    }

    public VeryLong un_minus() {
        VeryLong val = new VeryLong(this);
        val.isNegative = !val.isNegative;
        return val;
    }

    public boolean less(VeryLong sec) {
        // Проверяем, что не -0
        boolean negative_empty = this.isNegative && this.number.isEmpty();
        int sec_num = sec.number.size();
        int cur_num = this.number.size();
        if (negative_empty || (cur_num == 1 && this.number.get(cur_num - 1) == 0)) {
            if (sec.number.isEmpty() || (sec_num == 1 && sec.number.get(sec_num - 1) == 0)) {
                return false;
            }

            if (sec.isNegative) {
                return false;
            }
            if (!sec.isNegative) {
                return true;
            }
        }

        if (this.isNegative && !sec.isNegative) {
            return true;
        }

        if (this.number.size() < sec.number.size()) {
            return true;
        } else if (this.number.size() > sec.number.size()) {
            return false;
        }

        for (int i = sec.number.size() - 1; i >= 0; --i) {
            if (this.number.get(i) < sec.number.get(i)) {
                return true;
            } else if (this.number.get(i) > sec.number.get(i)){
                return false;
            }
        }

        return false;
    }

    public boolean equal (VeryLong sec) {
        return !this.less(sec) && !sec.less(this);
    }

    public boolean notEq (VeryLong sec) {
        return !this.equal(sec);
    }

    public boolean lessEq (VeryLong sec) {
        return this.less(sec) || this.equal(sec);
    }

    public boolean greater (VeryLong sec) {
        return sec.less(this);
    }

    public boolean greaterEq (VeryLong sec) {
        return this.greater(sec) || this.equal(sec);
    }

    public static VeryLong add(VeryLong left, VeryLong right) {
        VeryLong local_left = new VeryLong(left);
        VeryLong local_right = new VeryLong(right);
        VeryLong zero = new VeryLong("0");

        boolean is_inverted = false;
        if (local_left.less(zero) && local_right.less(zero)) {
            local_left = local_left.un_minus();
            local_right = local_right.un_minus();
            is_inverted = true;
        } else if (local_right.less(zero)) {
            return minus(local_left, local_right.un_minus());
        } else if (local_left.less(zero)) {
            return minus(local_right, local_left.un_minus());
        }

        // Складывает два неотрицательных числа
        int carry = 0;
        boolean carry_bool = false;
        int max_size = max(local_left.number.size(), local_right.number.size());
        for (int i = 0; (i < max_size) || carry_bool; ++i) {
            if (i == local_left.number.size()) {
                local_left.number.add(0);
            }
            local_left.number.set(i, local_left.number.get(i) + carry);
            if (i < local_right.number.size()) {
                local_left.number.set(i, local_left.number.get(i) + local_right.number.get(i));
            }
            carry_bool = local_left.number.get(i) >= base;
            carry = carry_bool ? 1 : 0;
            if (carry_bool) {
                local_left.number.set(i, local_left.number.get(i) - base);
            }


        }

        if (is_inverted) {
            return local_left.un_minus();
        } else {
            return local_left;
        }
    }

    public static VeryLong minus(VeryLong left, VeryLong right) {
        VeryLong local_left = new VeryLong(left);
        VeryLong local_right = new VeryLong(right);
        VeryLong zero = new VeryLong("0");
        boolean is_inverted = false;

        if (local_left.less(zero) && local_right.less(zero)) {
            local_left = local_left.un_minus();
            local_right = local_right.un_minus();
            is_inverted = true;
        } else if (local_right.less(zero)) {
            return add(local_left, local_right.un_minus());
        } else if (local_left.less(zero)) {
            return add(local_left.un_minus(), local_right).un_minus();
        }
        if (local_left.less(local_right)) {
            VeryLong temp = local_left;
            local_left = local_right;
            local_right = temp;
            is_inverted = true;
        }

        // Вычитает два неотрицательных числа, left >= right
        int carry = 0;
        boolean carry_bool = false;
        for (int i = 0; i < local_right.number.size() || carry_bool; ++i) {
            int temp_number_i = local_left.number.get(i);
            temp_number_i -= carry;
            if (i < local_right.number.size()) {
                temp_number_i -= local_right.number.get(i);
            }


            carry_bool = temp_number_i < 0;
            carry = carry_bool ? 1 : 0;
            if (carry_bool) {
                temp_number_i += base;
            }
            local_left.number.set(i, temp_number_i);
        }

        // Удаляем лишние 0
        while (local_left.number.size() > 1 && local_left.number.get(local_left.number.size() - 1) == 0) {
            local_left.number.remove(local_left.number.size() - 1);
        }

        if (is_inverted) {
            return local_left.un_minus();
        } else {
            return local_left;
        }
    }


    public static VeryLong multiply(VeryLong left, VeryLong right) {
        VeryLong res = new VeryLong(0);
        res.number = new ArrayList<Integer>();
        for (int i = 0; i < left.number.size() + right.number.size(); i++) {
            res.number.add(0);
        }

        for (int i = 0; i < left.number.size(); ++i) {
            boolean carry_bool = false;
            for (int j = 0, carry = 0; j < right.number.size() || carry_bool; ++j) {
                long cur = res.number.get(i+j) + carry;
                if (j < (int) right.number.size()) {
                    cur += left.number.get(i) * right.number.get(j);
                }
                res.number.set(i+j, (int) (cur % base));
                carry = (int)( cur / base );

                carry_bool = carry != 0;
            }
        }
        while (res.number.size() > 1 && res.number.get(res.number.size() - 1) == 0) {
            res.number.remove(res.number.size() - 1);
        }
        return ((left.isNegative ^ right.isNegative) ? res.un_minus() : res);
    }

    public static VeryLong divWithInt (VeryLong left, int right) {
        VeryLong local_left = new VeryLong(left);
        int carry = 0;
        int size = local_left.number.size();
        for (int i = size - 1; i >= 0; --i) {
            int cur = local_left.number.get(i) + carry * base;
            local_left.number.set(i, cur / right);
            carry = cur % right;
        }
        while (local_left.number.size() > 1 && local_left.number.get(local_left.number.size() - 1) == 0) {
            local_left.number.remove(local_left.number.size() - 1);
        }
        VeryLong local_right = new VeryLong(right < 0 ? "-1" : "1");
        return new VeryLong(multiply(local_left, local_right));
    }

    public static VeryLong div (VeryLong left, VeryLong right) {
        if(right.number.size() == 1 && right.number.get(0) == 2) {
            return divWithInt(left, 2);
        }
        boolean sign_is_negative = (left.isNegative) ^(right.isNegative);
        VeryLong local_left = new VeryLong(left);
        local_left.isNegative = false;
        VeryLong local_right = new VeryLong(right);
        local_right.isNegative = false;

        if (local_left.less(local_right)) {
            return new VeryLong(0);
        }
        if (local_left.equal(local_right)) {
            VeryLong res = new VeryLong(1);
            res.isNegative = sign_is_negative;
            return res;
        }

        VeryLong top = new VeryLong(divWithInt(local_left, 2));
        VeryLong bottom = new VeryLong(0);
        VeryLong one = new VeryLong(1);
        while (bottom.less(top)) {
            VeryLong sum = new VeryLong(add(top, bottom));
            VeryLong middle = new VeryLong(divWithInt(sum,  2));
            VeryLong multiply = new VeryLong(multiply(middle, local_right));
            if (multiply.less(local_left)) {
                if ( multiply(add(middle, one), local_right).greater(local_left) ) {
                    while (middle.number.size() > 1 && middle.number.get(middle.number.size() - 1) == 0) {
                        middle.number.remove(middle.number.size() - 1);
                    }
                    middle.isNegative = sign_is_negative;
                    return middle;
                }
                bottom = middle;
            } else if (multiply.greater(local_left)) {
                top = middle;
            } else {
                while (middle.number.size() > 1 && middle.number.get(middle.number.size() - 1) == 0) {
                    middle.number.remove(middle.number.size() - 1);
                }
                middle.isNegative = sign_is_negative;
                return middle;
            }
        }
        while (bottom.number.size() > 1 && bottom.number.get(bottom.number.size() - 1) == 0) {
            bottom.number.remove(bottom.number.size() - 1);
        }
        bottom.isNegative = sign_is_negative;
        return bottom;

    }

    public static VeryLong residue(VeryLong left, VeryLong right) {
        VeryLong divRes = div(left, right);
        return minus(left, multiply(divRes, right));
    }

    public String toString() {
        ArrayList<Integer> list = new ArrayList<Integer>(0);
        list.addAll(this.number);
        Collections.reverse(list);

        String ans = "";
        for (int el : list) {
            ans += Integer.toString(el);
        }

        if (this.isNegative) {
            ans = "-" + ans;
        }
        return ans;
    }

    public boolean toBoolean() {
        VeryLong zero = new VeryLong(0);
        if (this.equal(zero)) {
            return false;
        }
        return true;
    }

    public int toInt() {
        VeryLong maxInt = new VeryLong(Integer.MAX_VALUE);
        VeryLong minInt = new VeryLong(Integer.MIN_VALUE);

        if (!(this.lessEq(maxInt) && this.greaterEq(minInt))) {
            throw new RuntimeException();
        }

        ArrayList<Integer> list = this.number;
        Collections.reverse(list);
        int res = 0;
        for (int i = 0; i < list.size(); i++) {
            res = res*10 + list.get(i);
        }
        if (this.isNegative) {
            res *= -1;
        }
        return res;
    }

    public long toLong() {
        VeryLong maxInt = new VeryLong(Long.MAX_VALUE);
        VeryLong minInt = new VeryLong(Long.MIN_VALUE);

        if (!(this.lessEq(maxInt) && this.greaterEq(minInt))) {
            throw new RuntimeException();
        }

        ArrayList<Integer> list = this.number;
        Collections.reverse(list);
        long res = 0;
        for (int i = 0; i < list.size(); i++) {
            res = res*10 + list.get(i);
        }
        if (this.isNegative) {
            res *= -1;
        }

        return res;
    }

    private boolean isNegative;
    private ArrayList<Integer> number;
    private static int base = 10;
}
