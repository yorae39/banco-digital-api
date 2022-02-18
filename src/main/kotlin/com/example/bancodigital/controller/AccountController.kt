package com.example.bancodigital.controller

import com.example.bancodigital.dto.AccountDTO
import com.example.bancodigital.facade.AccountFacade
import com.example.bancodigital.model.Account
import com.example.bancodigital.model.response.AccountResponse
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/accounts")
@Api(tags = ["Account"])
class AccountController(
    val accountFacade: AccountFacade
) : AccountApi {

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ["/findAll"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findAll(): List<Account> {
        return accountFacade.findAll()
    }

    @RequestMapping(value = ["/{holderExternalKey}/save"],
        method = [RequestMethod.POST],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun createAccount(
        @PathVariable holderExternalKey: UUID,
        httpServletResponse: HttpServletResponse,
    ): ResponseEntity<Any> {
        val savedHolder = accountFacade.savedHolderByExternalKey(holderExternalKey.toString())
        if (savedHolder != null) {
            val account = AccountDTO.from(savedHolder)
            val validate = accountFacade.validations(holderExternalKey)
            return if (validate.isNotEmpty()) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validate)
            } else {
                val savedAccount = accountFacade.createAccount(account)
                ResponseEntity.status(HttpStatus.CREATED).body(savedAccount)
            }
        }
        val info = "Holder externalKey : $holderExternalKey not found"
        return ResponseEntity.ok(info)
    }

    @RequestMapping(value = ["/findById/{id}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findById(@PathVariable id: Long): ResponseEntity<Optional<Account>> {
        val account = accountFacade.findById(id)
        return if (!account.isPresent) ResponseEntity.notFound().build() else ResponseEntity.ok(account)
    }

    @RequestMapping(value = ["/findByExternalKey/{externalKey}"],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findByExternalKey(@PathVariable externalKey: String): ResponseEntity<AccountResponse> {
        val account = accountFacade.findByExternalKey(externalKey)
        return if (account == null) ResponseEntity.notFound().build() else ResponseEntity.ok(AccountResponse.from(
            account))
    }

    @RequestMapping(value = ["/transfer/{id}/{holderExternalKey}"],
        method = [RequestMethod.POST],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun transferAccountOtherHolder(@PathVariable id: Long, @PathVariable holderExternalKey: UUID): ResponseEntity<Any> {
        val savedHolder = accountFacade.savedHolderByExternalKey(holderExternalKey.toString())
        if (savedHolder != null) {
            val account = AccountDTO.from(savedHolder)
            val validate = accountFacade.validations(account.holder.externalKey)
            return if (validate.isNotEmpty()) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validate)
            } else {
                val savedAccount = accountFacade.updateAccount(id, account)
                ResponseEntity.ok(AccountResponse.from(savedAccount))
            }
        }
        val info = "Holder externalKey : $holderExternalKey not found"
        return ResponseEntity.ok(info)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = ["/update/{id}/{active}"],
        method = [RequestMethod.PUT],
        consumes = [MediaType.ALL_VALUE])
    override fun updateActiveProperty(@PathVariable id: Long, @PathVariable active: Boolean): ResponseEntity<String> {
        val info = accountFacade.updateActiveProperty(id, active)
        return ResponseEntity.ok(info)
    }

}