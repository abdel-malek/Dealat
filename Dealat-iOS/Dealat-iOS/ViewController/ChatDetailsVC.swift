//
//  ChatDetailsVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/11/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import GrowingTextView
import IQKeyboardManagerSwift
import SwiftyJSON
import AFDateHelper
import Foundation
import AVFoundation


class ChatDetailsVC: BaseVC {

    @IBOutlet weak var textView: GrowingTextView!
    @IBOutlet weak var textViewBottomConstraint: NSLayoutConstraint!
    @IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var sendBtn : UIButton!
    
    var chat = Chat()
    var messages = [Message]()
    var ad : AD!
    
    var messages2 = [(String,[Message])]()
    
    static var messagesRT = [(TimeInterval,Bool)]()
    static var isSending : Bool = false


    override func viewDidLoad() {
        super.viewDidLoad()

        ChatDetailsVC.messagesRT.removeAll()
        
        if Provider.isArabic{
//            sendBtn.titleLabel?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
//            sendBtn.imageView?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
        }
        
        textView.tintColor = Theme.Color.red
        
        IQKeyboardManager.sharedManager().disabledToolbarClasses = [ChatDetailsVC.self]
        IQKeyboardManager.sharedManager().disabledTouchResignedClasses = [ChatDetailsVC.self]
        IQKeyboardManager.sharedManager().disabledDistanceHandlingClasses = [ChatDetailsVC.self]
        
        let infoBtn = UIButton.init(type: .infoLight)
        infoBtn.addTarget(self, action: #selector(self.goToDetails), for: UIControlEvents.touchUpInside)
        self.navigationItem.rightBarButtonItem = UIBarButtonItem.init(customView: infoBtn)
        
        getData()
        
        Provider.setScreenName("ChatActivity")

    }
    
    @objc func goToDetails(){
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdDetailsBaseVC") as! AdDetailsBaseVC
        
        var tamplateId = 1
        var adTitle = ""
        
        if self.chat.template_id != nil{
            tamplateId = self.chat.template_id.intValue
            adTitle = self.chat.ad_title ?? ""
        }else if self.ad != nil{
            tamplateId = self.ad.tamplate_id.intValue
            adTitle = self.ad.title
        }
        
        vc.tamplateId = tamplateId
        vc.ad = AD.init(JSON : ["title" : adTitle,"ad_id" : self.chat.ad_id.intValue,"tamplate_id" : tamplateId, "category_id" : 0])
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)

    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(true)
    }

    @objc override func getNewMessage(_ not : Notification){
        var isHere = false
        
        
        if let notification = not.userInfo {
            print("handleNotificationTapping: \(notification)")
            
            do{
                let not = JSON(notification["ntf_type"])
                print("TTT : \(not.intValue)")
                let type = not.intValue
                print("TYPE : \(type)")
                if type == 1{
                    print("BODY : \(notification["ntf_body"])")
                    
                    if let payload = notification["ntf_body"]
                    {
                        print("ntf_body: \(payload)")
                        let bod = JSON(payload)
                        do{
                            let dd = bod.stringValue.data(using: String.Encoding.utf8)
                            let ii = try JSONSerialization.jsonObject(with: dd!, options: JSONSerialization.ReadingOptions.mutableLeaves)
                            let tt  = JSON(ii)
                            if let obj = tt.dictionaryObject{
                                if let chat = Chat(JSON: obj){
                                    if let id1 = chat.chat_session_id, let id2 = self.chat.chat_session_id{
                                        print("IDDD1 : \(id1.intValue) -- IDDDD2 \(id2.intValue)")
                                        if id1.intValue == id2.intValue{
                                            isHere = true
                                            
                                            var body = ""
                                            
                                            if let aps = notification["aps"] as? [String: AnyObject]{
                                                if let msg = aps["alert"] as? [String: AnyObject]
                                                {
                                                    body = ((msg["body"] as? String) != nil) ? msg["body"] as! String : ""
                                                }
                                            }

                                            let msg = Message(JSON: ["text" : body.emojiEscapedString,"to_seller" : JSON(false),"chat_session_id" : id1.intValue, "created_at" : chat.modified_at])

                                            self.messages.append(msg!)
                                            
                                            var soundId: SystemSoundID = 0
                                            let bundle = Bundle.main
                                            guard let soundUrl = bundle.url(forResource: "sms", withExtension: "wav") else{
                                                return
                                            }
                                            AudioServicesCreateSystemSoundID(soundUrl as CFURL, &soundId)
                                            AudioServicesPlayAlertSound(1007)

                                            
                                            self.fixMessages()

//                                          self.getRefreshing()
                                        }
                                    }
                                }
                            }
                        }catch let err{
                            print("ERROR")
                            print(err.localizedDescription)
                        }
                    }
                }
            }catch let err{ print("ERROR: \(err.localizedDescription)")}
            
            
            if !isHere{
                let n = UILocalNotification.init()
                n.userInfo = not.userInfo
                if let aps = not.userInfo!["aps"] as? [String: AnyObject]{
                    if let msg = aps["alert"] as? [String: AnyObject]
                    {
                        n.alertTitle = ((msg["title"] as? String) != nil) ? msg["title"] as! String : "Dealat"
                        n.alertBody = ((msg["body"] as? String) != nil) ? msg["body"] as! String : ""
                    }
                }
                n.fireDate = Date()
                n.soundName = UILocalNotificationDefaultSoundName
                
                if !PushManager.LocalSchedules.contains(n.userInfo!["gcm.message_id"] as! String){
                    PushManager.LocalSchedules.append(n.userInfo!["gcm.message_id"] as! String)
                    UIApplication.shared.scheduleLocalNotification(n)
                }

            }
            
        }
    }
    
    
    override func setupViews() {
        // *** Customize GrowingTextView ***
        textView.layer.cornerRadius = 4.0
        textView.delegate = self
        textView.placeholder = "TypeHere".localized
        
        // *** Listen to keyboard show / hide ***
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillChangeFrame), name: NSNotification.Name.UIKeyboardWillChangeFrame, object: nil)
        
//        self.title = self.chat.ad_title
        
        let titleLbl = UILabel(frame: CGRect(x: 0, y: 8, width: 200, height: 200))
        var titleString = ""
        titleString += (self.chat.ad_title != nil) ? self.chat.ad_title : ""
        if let seller_id = self.chat.seller_id{
            titleString += "\n"
            if User.getCurrentUser().user_id == seller_id.intValue{
                titleString += (self.chat.user_name)
            }else{
                titleString += (self.chat.seller_name)
            }
        }
        titleLbl.text = titleString
        titleLbl.textColor = UIColor.white
        titleLbl.numberOfLines = 0
//        titleLbl.font = Theme.Font.CenturyGothic
        titleLbl.textAlignment = .center
        titleLbl.minimumScaleFactor = 0.5
        self.navigationItem.titleView = titleLbl

        
        tableView.delegate = self
        tableView.dataSource = self
        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 100
        
        self.tableView.register(UINib.init(nibName: "MessageCell", bundle: nil), forCellReuseIdentifier: "MessageCell")

    }
    
    override func getRefreshing() {
        
        Communication.shared.get_chat_messages(chat: self.chat) { (res) in
            self.hideLoading()
            self.textView.text = nil

            self.tableView.alpha = 0
            self.messages = res
            self.fixMessages()
        }
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
    
    
    func fixMessages(){
        var messagesTemp = [Message]()
//        var messages3 =  [(Date,[Message])]()
        var messages3 =  [(String,[Message])]()

        
        for i in self.messages{
            messagesTemp.append(Message.init(JSON : i.toJSON())!)
        }
        
        
//        var temp = [Message]()
        var temp = [String]()

        
        for i in messagesTemp{
            
//            if !temp.contains(where: {$0.getDate() == i.getDate()}){
//                temp.append(Message.init(JSON : i.toJSON())!)
//            }
            
            if !temp.contains(i.getDateOnlyString()) {
                temp.append(i.getDateOnlyString())
            }
            
        }
        
        for i in temp{
//            let mesgs = messagesTemp.filter({$0.getDate() == i.getDate()}).sorted(by: { (m1, m2) -> Bool in
//                return m1.getDateFull().compare(DateComparisonType.isEarlier(than: m2.getDateFull()))
//            })
            
            let mesgs = messagesTemp.filter({$0.getDateOnlyString() == i}).sorted { (m1, m2) -> Bool in
                return m1.getDateFull().compare(DateComparisonType.isEarlier(than: m2.getDateFull()))
            }

            
            messages3.append((i, mesgs))
        }
        
        
        self.messages2 = messages3
        
        self.tableView.reloadData()
        
        
        if let m = messages3.last{
            DispatchQueue.main.async {
                
                if UIDevice().userInterfaceIdiom == .phone {
                    switch UIScreen.main.nativeBounds.height  {
                    case 2436,2688,1792: break
                    default:
                        let indexPath = IndexPath.init(row: m.1.count - 1, section: self.messages2.count - 1)
                        self.tableView.scrollToRow(at: indexPath, at: UITableViewScrollPosition.bottom, animated: false)
                    }
                }
                
                self.tableView.alpha = 1
            }
        }else{
            self.tableView.alpha = 1
        }
    }
    
    @objc private func keyboardWillChangeFrame(_ notification: Notification) {
        
        if let endFrame = (notification.userInfo?[UIKeyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue {
            var keyboardHeight = view.bounds.height - endFrame.origin.y
            
            print(keyboardHeight)
            
            if #available(iOS 11, *) {
                if keyboardHeight > 0 {
                    keyboardHeight = keyboardHeight - view.safeAreaInsets.bottom
                }
            }
            
            textViewBottomConstraint.constant = keyboardHeight
            view.layoutIfNeeded()
        }
    }
    
    func scrollViewWillBeginDragging(_ scrollView: UIScrollView) {
        print(scrollView.bounds.origin.y)
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == tableView{
//        let scrollViewHeight = scrollView.frame.size.height;
//        let scrollContentSizeHeight = scrollView.contentSize.height;
        let scrollOffset = scrollView.contentOffset.y;
        
        if scrollOffset < -80{
            self.textView.resignFirstResponder()
        }

//        if (scrollOffset == 0)
//        {
//            // then we are at the top
//        }
//        else if (scrollOffset + scrollViewHeight == scrollContentSizeHeight)
//        {
//            print("true")
//        }
        }
    }
    
    func fixMessages2(){
        
        var chat_session_id : Int!
        if self.chat.chat_session_id != nil {
            chat_session_id = self.chat.chat_session_id.intValue
        }
        
        print("-----")
        for mm in 0..<self.messages.count{
            

            if self.messages[mm].isNew,!ChatDetailsVC.isSending{
                ChatDetailsVC.isSending = true
                print("--\(self.messages[mm].text!)---")

                Communication.shared.send_msg(ad_id: self.chat.ad_id.intValue,chat_session_id: chat_session_id, msg: self.messages[mm].text.emojiEscapedString, callback: { (res) in
                    

                    self.messages[mm].isNew = false
                    self.messages[mm].created_at = res.created_at
                    
                    for s in 0..<self.messages2.count{
                        for i in 0..<self.messages2[s].1.count{
                            
                            if self.messages2[s].1[i].timeStamp == self.messages[mm].timeStamp{
                                self.messages2[s].1[i].isNew = false
                                self.messages2[s].1[i].created_at = res.created_at
                                self.tableView.reloadRows(at: [IndexPath.init(row: i, section: s)], with: UITableViewRowAnimation.fade)
                            }
                        }
                    }
                    
                    ChatDetailsVC.isSending = false
                    
                    self.fixMessages()
                    self.fixMessages2()
                })
                break
            }
        }
            print("-----")
    }
    
    @IBAction func sendAction(){
//        self.textView.resignFirstResponder()
        let message = self.textView.text!
        
        if message.isEmpty{
            self.showErrorMessage(text: "Please type a message")
        }else{
//            self.showLoading()
            
            var chat_session_id : Int!
            if self.chat.chat_session_id != nil {
                chat_session_id = self.chat.chat_session_id.intValue
            }
            
            self.textView.text = nil
            
            let timeStamp = Date().timeIntervalSince1970
            print("Date.init().timeIntervalSinceNow : \(timeStamp)")

            let msg = Message(JSON: ["text" : message.emojiEscapedString,"to_seller" : JSON(true),"chat_session_id" : chat_session_id,"isNew" : true,"timeStamp" : timeStamp, "created_at" : Date().toString(format: DateFormatType.custom("yyyy-MM-dd HH:mm:ss"))])
            msg?.isNew = true
            
            self.messages.append(msg!)
            
            self.fixMessages()
            self.fixMessages2()
            
//            var message_id : JSON!
//            var text : String!
//            var to_seller : JSON!
//            var chat_session_id : JSON!
//            var created_at : String!
//            var modified_at : String!

            
//            Communication.shared.send_msg(ad_id: self.chat.ad_id.intValue,chat_session_id: chat_session_id, msg: message.emojiEscapedString, callback: { (res) in
//
//                self.getRefreshing()
//            })
            
        }
    }
    
    override func onTouch() {
        
    }
}

extension ChatDetailsVC : UITableViewDelegate, UITableViewDataSource{
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return self.messages2.count
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.messages2[section].1.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MessageCell", for: indexPath) as! MessageCell
        
        var chat_session_id : Int!
        if self.chat.chat_session_id != nil {
            chat_session_id = self.chat.chat_session_id.intValue
        }

        cell.chat = self.chat
        cell.chat_session_id = chat_session_id
        cell.message = self.messages2[indexPath.section].1[indexPath.row]
        return cell
    }
    
    
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return self.messages2[section].0
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.textView.resignFirstResponder()
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        let vv = UIView(frame: CGRect.init(x: 0, y: 0, width: self.view.frame.width, height: 40))
        vv.backgroundColor = .clear
        
        
        let ll = UILabel()
        ll.font = Theme.Font.Calibri
        ll.text = self.messages2[section].0
        ll.backgroundColor = UIColor.gray
        ll.textColor = . white
        ll.textAlignment = .center
        ll.sizeToFit()
        ll.center = vv.center
        
        let vv2 = UIView.init()
        vv2.frame.size.width = ll.bounds.width + 8
        vv2.frame.size.height = ll.bounds.height + 8
        vv2.center = ll.center
        vv2.backgroundColor = .gray
        vv2.cornerRadius = 5

        
        vv.addSubview(vv2)
        vv.addSubview(ll)

        
        return vv
    }
    
}

extension ChatDetailsVC: GrowingTextViewDelegate {
    
    // *** Call layoutIfNeeded on superview for animation when changing height ***
    func textViewDidChangeHeight(_ textView: GrowingTextView, height: CGFloat) {
        UIView.animate(withDuration: 0.3, delay: 0.0, usingSpringWithDamping: 0.7, initialSpringVelocity: 0.7, options: [.curveLinear], animations: { () -> Void in
            self.view.layoutIfNeeded()
        }, completion: nil)
    }
    
    
    func textViewDidBeginEditing(_ textView: UITextView) {
        print("textViewDidBeginEditing")
        
        if let m = self.messages2.last{
            DispatchQueue.main.async {
                
                if UIDevice().userInterfaceIdiom == .phone {
                    switch UIScreen.main.nativeBounds.height  {
                    case 2436,2688,1792: break
                    default:
                        self.tableView.scrollToRow(at: IndexPath.init(row: m.1.count - 1, section: self.messages2.count - 1), at: UITableViewScrollPosition.bottom, animated: false)
                    }
                }
                
            }
        }
    }
    
}

