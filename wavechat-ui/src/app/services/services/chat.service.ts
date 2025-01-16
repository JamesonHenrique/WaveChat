/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { ChatResponse } from '../models/chat-response';
import { createChat } from '../fn/chat/create-chat';
import { CreateChat$Params } from '../fn/chat/create-chat';
import { getChatsByReceiver } from '../fn/chat/get-chats-by-receiver';
import { GetChatsByReceiver$Params } from '../fn/chat/get-chats-by-receiver';
import { StringResponse } from '../models/string-response';


/**
 * Operações relacionadas ao chat
 */
@Injectable({ providedIn: 'root' })
export class ChatService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getChatsByReceiver()` */
  static readonly GetChatsByReceiverPath = '/api/v1/chats';

  /**
   * Obter chats por receptor.
   *
   * Obtém todos os chats onde o usuário autenticado é o receptor
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getChatsByReceiver()` instead.
   *
   * This method doesn't expect any request body.
   */
  getChatsByReceiver$Response(params?: GetChatsByReceiver$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ChatResponse>>> {
    return getChatsByReceiver(this.http, this.rootUrl, params, context);
  }

  /**
   * Obter chats por receptor.
   *
   * Obtém todos os chats onde o usuário autenticado é o receptor
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getChatsByReceiver$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getChatsByReceiver(params?: GetChatsByReceiver$Params, context?: HttpContext): Observable<Array<ChatResponse>> {
    return this.getChatsByReceiver$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<ChatResponse>>): Array<ChatResponse> => r.body)
    );
  }

  /** Path part for operation `createChat()` */
  static readonly CreateChatPath = '/api/v1/chats';

  /**
   * Criar um novo chat.
   *
   * Cria um novo chat entre dois usuários
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createChat()` instead.
   *
   * This method doesn't expect any request body.
   */
  createChat$Response(params: CreateChat$Params, context?: HttpContext): Observable<StrictHttpResponse<StringResponse>> {
    return createChat(this.http, this.rootUrl, params, context);
  }

  /**
   * Criar um novo chat.
   *
   * Cria um novo chat entre dois usuários
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createChat$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createChat(params: CreateChat$Params, context?: HttpContext): Observable<StringResponse> {
    return this.createChat$Response(params, context).pipe(
      map((r: StrictHttpResponse<StringResponse>): StringResponse => r.body)
    );
  }

}
