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

class ChatDetailsVC: BaseVC {

    @IBOutlet weak var textView: GrowingTextView!
    @IBOutlet weak var textViewBottomConstraint: NSLayoutConstraint!
    @IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var sendBtn : UIButton!
    
    var chat = Chat()
    var messages = [Message]()
    
    var messages2 = [(Date,[Message])]()

    override func viewDidLoad() {
        super.viewDidLoad()

        if Provider.isArabic{
//            sendBtn.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
            sendBtn.titleLabel?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
            sendBtn.imageView?.transform = CGAffineTransform(scaleX: -1.0, y: 1.0)
        }
        
        IQKeyboardManager.sharedManager().disabledToolbarClasses = [ChatDetailsVC.self]
        IQKeyboardManager.sharedManager().disabledTouchResignedClasses = [ChatDetailsVC.self]
        IQKeyboardManager.sharedManager().disabledDistanceHandlingClasses = [ChatDetailsVC.self]
        
        getData()
        
        Provider.setScreenName("Chat details")

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

           
            self.messages = res
            
            var messagesTemp = [Message]()
            var messages3 =  [(Date,[Message])]()

            
            for i in self.messages{
                messagesTemp.append(Message.init(JSON : i.toJSON())!)
            }
            
            
            var temp = [Message]()
            
            for i in messagesTemp{
                if !temp.contains(where: {$0.getDate() == i.getDate()}){
                    temp.append(Message.init(JSON : i.toJSON())!)
                }
            }
            
            for i in temp{
                let mesgs = messagesTemp.filter({$0.getDate() == i.getDate()}).sorted(by: { (m1, m2) -> Bool in
                   return m1.getDateFull().compare(DateComparisonType.isEarlier(than: m2.getDateFull()))
                })
                
                messages3.append((i.getDate(), mesgs))
            }
            

            self.messages2 = messages3
            
                self.tableView.reloadData()


            if let m = messages3.last{
                self.tableView.scrollToRow(at: IndexPath.init(row: m.1.count - 1, section: self.messages2.count - 1), at: UITableViewScrollPosition.bottom, animated: false)
            }
            
        }
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
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
            
            Communication.shared.send_msg(ad_id: self.chat.ad_id.intValue,chat_session_id: chat_session_id, msg: message, callback: { (res) in
                
                self.getRefreshing()
            })
            
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
        
        cell.message = self.messages2[indexPath.section].1[indexPath.row]
        return cell
    }
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return self.messages2[section].0.toString()
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        let vv = UIView(frame: CGRect.init(x: 0, y: 0, width: self.view.frame.width, height: 40))
        vv.backgroundColor = .clear
        
        
        let ll = UILabel()
        ll.font = Theme.Font.Calibri
        ll.text = self.messages2[section].0.toString(format: DateFormatType.isoDate)
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
//            self.tableView.scrollToRow(at: IndexPath.init(row: m.1.count - 1, section: self.messages2.count - 1), at: UITableViewScrollPosition.bottom, animated: false)
        }
    }
    
}

