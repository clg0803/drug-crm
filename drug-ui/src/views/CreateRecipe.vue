<template>
  <div id="app">
    <el-container style="height: 700px; border: 1px solid #eee">

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <el-input
              v-model="searchValue" size="mini" clearable
              placeholder="请输入药品名称查询" style="width:200px"></el-input>
          <el-button type="primary" size="mini" @click="doFilter">查询</el-button>
          <span> username 在这里显示</span>
        </el-header>

        <el-main>
          <h1> This is CreateRecipe </h1>
          <el-button type="success" size="small" @click="add">增加</el-button>
          <el-table :data="tableData.slice((currentPage-1)*pageSize, currentPage*pageSize)">
            <el-table-column prop="drugID" label="药品ID" width="100" align="center">
            </el-table-column>
            <el-table-column prop="drugName" label="药品名字" width="200" align="center">
            </el-table-column>
            <el-table-column prop="description" label="药品描述" width="300" style="text-align: center" align="center">
            </el-table-column>
            <el-table-column prop="drugClass" label="药品类型" width="100" align="center">
            </el-table-column>
            <el-table-column prop="inventory" label="药品库存" width="100" align="center">
            </el-table-column>
            <el-table-column prop="unit" label="药品单位" width="100" align="center">
            </el-table-column>

            <el-table-column label="ops" align="center">
              <template slot-scope="scope">
                <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                <el-button size="mini" type="danger" @click="remove(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination
              background
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[5, 10, 20, 40]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="tableData.length"
          >
          </el-pagination>

        </el-main>


      </el-container>


    </el-container>

    <el-dialog :title="dialogTitle" width="50%" :visible.sync="iconFormVisible">
      <el-form :inline="false" :model="userInfo" class="demo-form-inline">
        <el-form-item label="药品名">
          <el-input v-model="userInfo.drugName" ></el-input>
        </el-form-item>
        <el-form-item label="药效描述">
          <el-input v-model="userInfo.description"></el-input>
        </el-form-item>
        <el-form-item label="药品类型">
          <el-input v-model="userInfo.drugClass" placeholder="3-通用 2-中药 1-西药"></el-input>
        </el-form-item>
        <el-form-item label="库存">
          <el-input v-model="userInfo.inventory"></el-input>
        </el-form-item>
        <el-form-item label="单位">
          <el-input v-model="userInfo.unit"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="iconFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitUser()">确 定</el-button>
      </div>
    </el-dialog>

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
import axios from 'axios';

export default {
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      userInfo: {},
      dialogTitle: '增加',
      iconFormVisible: false,
      rowIndex: null,
      tableData: [],
      searchValue: "",
    }
  },
  methods: {
    doFilter() {
      console.log("into doFilter() method ... ")
      // TODO
      this.tableData.filter((item) => {
        // if (item.drugName.indexOf(this.searchValue) > -1) {
        //   this.searchData.push(item)
        // }
      })
      // this.tableData = this.searchData
    },
    // 增加
    add() {
      this.dialogTitle = '增加';
      this.userInfo = {};
      this.iconFormVisible = true;
    },
    // 编辑
    handleEdit(index, row) {
      this.dialogTitle = '编辑';
      this.userInfo = row;
      this.iconFormVisible = true;
      this.rowIndex = index;
    },
    // 删除
    remove(index, row) {
      // TODO
      this.$confirm(`确定删除${row.drugName}吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error',
      }).then(() => {
        this.tableData.splice(index, 1);
      });
    },
    submitUser() {
      if (this.dialogTitle == '编辑') {
        // this.tableData.splice(this.rowIndex, 1, this.userInfo);
        // TODO
        console.log(this.userInfo.drugID + " -- name : " + this.userInfo.drugName);
      } else if (this.dialogTitle == '增加') {

      }
      this.iconFormVisible = false;

      location.reload();
      return;
    },
    handleSizeChange: function(size) {
      this.pageSize = size
      console.log(this.pagesize)
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      console.log(this.currentPage)
    }
  },
  mounted() {
    // 填充数据
    this.$axios.get("http://localhost:8080/api/drug/home/all/")
        .then(response => {
          if(response.data.code === 0){
            console.log(response.data.data);
            this.tableData = [];
            this.tableData = response.data.data;
          }
        })
        .catch(error => {
          console.log(error.message);
          alert(error.message);
        })
  }
};
</script>
