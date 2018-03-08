//
//  MyChats.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/8/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import XLPagerTabStrip

class MyChatsVC: BaseVC,UICollectionViewDelegate,UICollectionViewDataSource,IndicatorInfoProvider {
    
    @IBOutlet weak var collectionView : UICollectionView!
    var chats = [Chat]()
    var itemInfo = IndicatorInfo(title: "My Chats".localized)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let flowLayout = collectionView.collectionViewLayout as? UICollectionViewFlowLayout {
            flowLayout.estimatedItemSize = CGSize(width: 1,height: 1)
        }

        getData()
    }
    
    override func setupViews() {
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
    }
    
    override func getRefreshing() {
        Communication.shared.get_my_chat_sessions { (res) in
            self.hideLoading()
            self.chats = res
            self.collectionView.reloadData()
        }
    }
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return chats.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! ChatCell
        cell.chat = self.chats[indexPath.row]
        return cell
        
        
    }
    
    
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
//        let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdDetailsBaseVC") as! AdDetailsBaseVC
//        vc.tamplateId = self.ads[indexPath.row].tamplate_id.intValue
//        vc.ad = self.ads[indexPath.row]
//        self.navigationController?.pushViewController(vc, animated: true)
    }
    
//    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
//
//        return CGSize.init(width: collectionView.frame.width, height: UIScreen.main.bounds.width / 4)
//    }
    
    
    
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return self.itemInfo
    }
    
}

