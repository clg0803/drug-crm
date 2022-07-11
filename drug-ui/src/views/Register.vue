<template>
  <div id="login">
    <el-container>
      <!-- 登陆表单区域 -->
      <el-form :model="regForm" :rules="loginFormRules">
        <!-- 用户名 -->
        <el-form-item prop="username" label="用户名">
          <el-input v-model="regForm.username" :disabled="this.lockInput"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password" label="密码">
          <el-input v-model="regForm.password" type="password" :disabled="this.lockInput"></el-input>
        </el-form-item>

        <el-select v-model="regForm.ident" clearable placeholder="请选择注册身份" :disabled="this.lockInput">
          <el-option
              v-for="item in options"
              :key="item.ident"
              :label="item.label"
              :value="item.ident">
          </el-option>
        </el-select>

        <el-form-item prop="email" label="邮箱">
          <el-input v-model="regForm.email"></el-input>
        </el-form-item>
        <el-button type="primary" @click="doSendCode">发送验证码</el-button>

        <el-form-item prop="vcode" label="验证码">
          <el-input v-model="regForm.vcode"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="doRegister">注册</el-button>
          <el-button type="info" @click="doReset">重置</el-button>


        </el-form-item>
      </el-form>

    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 这是登陆表单的数据绑定对象
      regForm: {
        username: '',
        password: '',
        ident: '',
        email: '',
        vcode: '',
      },
      lockInput: false,
      options: [{
        ident: 3,
        label: "管理员"
      }, {
        ident: 2,
        label: "中医"
      }, {
        ident: 1,
        label: "西医"
      }],

      // 这是表单的验证规则
      loginFormRules: {
        username: [
          // trigger: 'blur' -> 失去焦点触发
          {required: true, message: '请输入登陆名称', trigger: 'blur'},
          {min: 0, max: 20, message: '长度在 0 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入登陆密码', trigger: 'blur'},
          {min: 0, max: 10, message: '长度在 0 到 10 个字符', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱地址', trigger: 'blur'},
        ],
        vcode: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
          {min: 6, max: 6, message: '长度 6 个字符', trigger: 'blur'}
        ],
      }
    }
  }, methods: {
    doRegister() {
      const that = this;
      console.log(this.regForm.ident)
      if (!this.regForm.ident) {
        that.$confirm("没有选择身份信息", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: 'warning',
        }).then(() => {
          return false;
        });
      } else {
        const username = this.regForm.username;
        const password = this.regForm.password;
        const vCode    = this.regForm.vcode;
        const ident    = this.regForm.ident;
        console.log(username + " <=> " + password + " <=> " + vCode + " <=> " + ident)
        axios.get('http://localhost:8080/api/hr/doValidateAndStoreInfo', {
          params:{
            username, password, vCode, ident
          }
        }).then((response) => {
          const respType = response.data.type;
          console.log('get请求成功', respType);
          if (respType === "username undefined") {
            that.$confirm("该用户未通过邮箱注册", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'warning',
            }).then(() => {
              return false;
            });
          } else if (respType === "vCode mismatched") {
            that.$confirm("输入的验证码有误", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'error',
            }).then(() => {
              return false;
            });
          } else {
            that.$confirm("验证成功 !!!", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'success',
            }).then(() => {
              return true;
            });
          }
        }).catch((err) => {
          console.log('get请求失败', err)
        })
      }
    },
    doSendCode() {
      const that = this;
      const username = this.regForm.username;
      const email    = this.regForm.email;
      const ident    = this.regForm.ident;
      console.log(username + " <=> " + ident + " <=> " + email);
      const emailPattern = /^[a-zA-Z\d_.+-]+@[a-zA-Z\d-]+\.[a-zA-Z\d-.]+$/;
      if (!emailPattern.test(email)) {
        that.$confirm("邮箱地址格式不正确", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: 'warning',
        }).then(() => {
          return false;
        });
      } else {
        // send code
        axios.get(
            'http://localhost:8080/api/hr/saveAndSendCode',
            {
              params: {
                username, email, ident
              }
            }
        ).then((response) => {
          const respType = response.data.type;
          console.log('get请求成功', respType)
          if (respType === 'duplicate username') {
            that.$confirm("该用户名已被注册", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'warning',
            }).then(() => {
              return false;
            });
          } else if (respType === "email-sending failed") {
            that.$confirm("验证码发送出错 请联系管理员", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'error',
            }).then(() => {
              return false;
            });
          } else {
            that.$confirm('验证码已发送 请注意查收', "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'success',
            }).then(() => {
              this.lockInput = true;
              return true;
            });
          }
        }).catch((err) => {
          console.log('get请求失败', err)
        })
      }

    },
    doReset() {
      console.log(this.ident)
      this.ident = '';
      this.regForm = {}
    }
    }

};
</script>

