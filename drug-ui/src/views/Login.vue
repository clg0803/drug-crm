<template>
  <div id="login">
    <el-container>
      <!-- 登陆表单区域 -->
      <el-form :model="loginForm" :rules="loginFormRules">
        <!-- 用户名 -->
        <el-form-item prop="username" label="用户名">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password" label="密码">
          <el-input v-model="loginForm.password" type="password"></el-input>
        </el-form-item>

        <el-select v-model="ident" clearable placeholder="请选择登录身份">
          <el-option
              v-for="item in options"
              :key="item.ident"
              :label="item.label"
              :value="item.ident">
          </el-option>
        </el-select>

        <!-- 按钮区域 -->
        <el-form-item>
          <el-button type="primary" @click="doLogin">登陆</el-button>
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
      loginForm: {
        username: '',
        password: ''
      },
      ident: '',
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
        ]
      }
    }
  }, methods: {
    doLogin() {
      const that = this;
      if (!this.ident) {
        that.$confirm("没有选择身份信息", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: 'warning',
        }).then(() => {
          return false;
        });
      } else {
        console.log(this.loginForm.username + " : " + this.loginForm.password + " : " + this.ident)
        const username = this.loginForm.username;
        const password = this.loginForm.password;
        const ident = this.ident;
        axios.get(
          'http://localhost:8080/api/hr/login/',
          {
            params: {
              username, password, ident
            }
          }
        ).then((response) => {
          const respType = response.data.type;
          console.log('get请求成功', respType)
          if (respType === 'non error') {
            // 登录成功
            that.$confirm("登录成功", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'success',
            }).then(() => {
            });
          } else {
            that.$confirm("登陆失败", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'error',
            }).then(() => {
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
      this.loginForm = {}
    }
  }
}
</script>

