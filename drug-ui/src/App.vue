<template>
  <div id="app">
    <el-container style="height: 700px; border: 1px solid #eee">

      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-openeds="['1', '2', '3']">

          <el-submenu index="1">
            <template slot="title"><i class="el-icon-s-shop"></i>导航一 药品管理</template>
            <el-menu-item index="1-1" @click="gotoLink('/homepage')"><i class="el-icon-setting"></i>管理药品</el-menu-item>
          </el-submenu>

          <el-submenu index="2">
            <template slot="title"><i class="el-icon-receiving"></i>导航二 处方管理</template>
            <el-menu-item index="2-1" @click="gotoLink('/createRecipe')"><i class="el-icon-s-data"></i>开处方</el-menu-item>
            <el-menu-item index="2-2" @click="gotoLink('/recipe')"><i class="el-icon-sort-down"></i>查看处方流水</el-menu-item>
          </el-submenu>

          <el-submenu index="3">
            <template slot="title"><i class="el-icon-warning"></i>导航三 账户管理</template>
            <el-menu-item index="3-1" @click="gotoLink('/')"><i class="el-icon-time"></i>切换登录用户</el-menu-item>
            <el-menu-item index="3-2" @click="gotoLink('/register')"><i class="el-icon-s-release"></i>注册账户</el-menu-item>
          </el-submenu>

        </el-menu>
      </el-aside>

      <el-container>

        <el-main>
          <router-view/>
        </el-main>

      </el-container>

    </el-container>



  </div>
</template>

<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>

<script>
  export default {
    methods:{
      gotoLink(page) {
        this.$router.replace(page);
      }
    }
  }
</script>
