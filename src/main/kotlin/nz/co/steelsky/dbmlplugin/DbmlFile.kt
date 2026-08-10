package nz.co.steelsky.dbmlplugin

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.util.childrenOfType
import com.intellij.psi.util.contextOfType
import nz.co.steelsky.dbmlplugin.psi.DbmlProjectDefinition

class DbmlFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, DbmlLanguage) {
    override fun getFileType(): FileType = DbmlFileType
    override fun toString(): String = "DBML File"

    fun getDatabaseType(): String? {
        val projectDefinition = this.childrenOfType<DbmlProjectDefinition>().firstOrNull()
        if (projectDefinition != null) {
            val property =
                projectDefinition.projectBody?.projectPropertyList?.find { it.projectPropertyName.text == "database_type" }
            if (property != null) {
                return property.projectPropertyValue?.text?.trim('"', '\'')
            }
        }
        return null
    }
}
