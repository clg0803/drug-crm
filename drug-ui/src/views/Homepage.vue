<template>
  <div id="app">
    <el-container style="height: 700px; border: 1px solid #eee">

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <el-input
              v-model="searchValue" size="mini" clearable
              placeholder="请输入药品名称查询" style="width:200px"></el-input>
          <el-button type="primary" size="mini" @click="doFilter">查询</el-button>
          <span> {{docType}} </span>
        </el-header>

        <el-main>
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
        <el-form-item label="药品ID">
          <el-input v-model="userInfo.drugID" placeholder="用于增加、不建议修改" :disabled="this.dialogTitle === '编辑'"></el-input>
        </el-form-item>
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
      tableData: [],
      searchValue: "",
      docType: "",
      centerDialogVisible: false
    }
  },
  methods: {
    doFilter() {
      console.log("into doFilter() method ... ")
      console.log(this.searchValue)
      // TODO
      let searchData = [];
      this.tableData.filter((item) => {
        if (item.drugName.search(this.searchValue) != -1) {
          searchData.push(item)
        }
      })
      console.log(searchData);
      this.tableData = searchData;
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
    },
    // 删除
    remove(index, row) {
      const that = this;
      this.$confirm(`确定删除 ${row.drugName} 吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        console.log(row.drugID);
        // TODO 确定删除
        axios.post("http://localhost:8080/api/drug/delete",
            row
        ).then(function (response) {
          console.log(response.data);
          if (response.data === true) {
            that.$confirm("删除成功", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'success',
            }).then(() => {
              location.reload();
            });
          } else {
            that.$confirm("删除失败 请联系管理员", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'error',
            }).then(() => {
              location.reload();
            });
          }
        }).catch(function (error) {
          console.log(error)
          that.$confirm("发生未知错误", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: 'warning',
          }).then(() => {
            location.reload();
          });
        });

      });
    },
    submitUser() {
      const drugEntity = this.userInfo;
      let that = this;
      if (this.nilExceptDesc(drugEntity)) {
        that.$confirm("除了 药效描述 之外的属性值都不能为空 ", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: 'warning',
        }).then(() => {
        });
        return false;
      }
      if (this.dialogTitle === '编辑') {
        axios.post("http://localhost:8080/api/drug/update",
            drugEntity
        ).then(function (response) {
          console.log(response.data);
          if (response.data === true) {
            that.$confirm("修改成功", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'success',
            }).then(() => {
              location.reload();
            });
          } else {
            that.$confirm("修改失败", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'error',
            }).then(() => {
              location.reload();
            });
          }
        }).catch(function (error) {
          console.log(error)
          that.$confirm("发生未知错误", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: 'warning',
          }).then(() => {
            location.reload();
          });
        });

      } else if (this.dialogTitle === '增加') {
        axios.post("http://localhost:8080/api/drug/add",
            drugEntity
        ).then(function (response) {
          console.log(response.data);
          if (response.data === true) {
            that.$confirm("成功增加一个药品", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'success',
            }).then(() => {
              location.reload();
            });
          } else {
            that.$confirm("添加失败 请联系管理员检查添加的药品 ID 是否重复", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: 'error',
            }).then(() => {
              location.reload();
            });
          }
        }).catch(function (error) {
          console.log(error)
          that.$confirm("发生未知错误", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: 'warning',
          }).then(() => {
            location.reload();
          });
        });
      }

      this.iconFormVisible = false;
      return;
    },
    nilExceptDesc(drugEntity) {
      let restrictKeyNum = 0;
      for (const key in drugEntity) {
        if (key !== 'description') {
          restrictKeyNum++;
        }
      }
      return restrictKeyNum !== 5;
    },
    handleSizeChange(size) {
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
            let ident = response.data.docType;
            switch (ident) {
              case 1:
                this.docType = '西医';
                break;
              case 2:
                this.docType = '中医';
                break;
              case 3:
                this.docType = '管理员';
                break;
            }
          }
        })
        .catch(error => {
          console.log(error.message);
          alert(error.message);
        })
  }
};
</script>
